package org.boofcv.android.detect;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;
import org.ddogleg.struct.FastQueue;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.abst.feature.detect.interest.ConfigSiftDetector;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.blur.BlurImageOps;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.blur.impl.ImplMedianHistogramInner;
import boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive;
import boofcv.alg.filter.blur.impl.ImplMedianSortNaive;
import boofcv.alg.filter.convolve.ConvolveImageMean;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoRenderProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.ScalePoint;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * @author Peter Abeles
 */
public class ScalePointDisplayActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener  {
	Spinner spinner;

	Paint paintMax;

	int active = -1;

	OMSServer server;
	DemoManager DM;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = getResources().getDisplayMetrics();


		InetSocketAddress host, omsHost;

		try {
			Registry registry = LocateRegistry.getRegistry("157.82.159.58", 22346);
			server = (OMSServer) registry.lookup("SapphireOMS");
			System.out.println(server);

			host = new InetSocketAddress("192.168.0.7", 22346);
			omsHost = new InetSocketAddress("157.82.159.58", 22346);
			nodeServer = new KernelServerImpl(host, omsHost);
			System.out.println(nodeServer);

			System.setProperty("java.rmi.server.hostname", host.getAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//Server is initiated with appObject to perform remote RPCs
			DM = (DemoManager) server.getAppEntryPoint();
			System.out.println("Got AppEntryPoint");
			//dm.LatencyCheck();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		paintMax = new Paint();
		paintMax.setColor(Color.RED);
		paintMax.setStyle(Paint.Style.STROKE);
		float strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, dm);
		paintMax.setStrokeWidth(strokeWidth);

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.detect_point_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinner = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.scale_features, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setSelection( spinner.getSelectedItemPosition() );
	}

	@Override
	protected void onPause() {
		super.onPause();
		active = -1;
	}

	private void setSelection( int which ) {
		if( which == active )
			return;
		active = which;

		//InterestPointDetector<GrayU8> detector;

		switch( which ) {
			case 0:
				//detector = FactoryInterestPoint.fastHessian(new ConfigFastHessian(10,3,100,2,9,4,4));
				DM.fastHessian(new ConfigFastHessian(10,3,100,2,9,4,4));
				break;

			case 1:
				ConfigSiftDetector configSift = new ConfigSiftDetector(200);
				//detector = FactoryInterestPoint.sift(null, configSift, GrayU8.class);
				DM.sift(null, configSift);
				break;

			default:
				throw new RuntimeException("Unknown selection");
		}

		setProcessing(new PointProcessing());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
		setSelection( pos );
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	protected class PointProcessing extends VideoRenderProcessing<GrayU8> {
		//InterestPointDetector<GrayU8> detector;

		Bitmap bitmap;
		byte[] storage;

		FastQueue<ScalePoint> foundGUI = new FastQueue<ScalePoint>(ScalePoint.class,true);
		//FastQueue<ScalePoint> foundGUI;
		public PointProcessing() {
			super(DM.single(GrayU8.class));
			//this.detector = detector;
		}

		@Override
		protected void declareImages(int width, int height) {
			super.declareImages(width, height);
			bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
			storage = ConvertBitmap.declareStorage(bitmap,storage);
		}

		@Override
		protected void process(GrayU8 gray) {
			DM.scaleDetect(gray);
			synchronized ( lockGui ) {
				ConvertBitmap.grayToBitmap(gray,bitmap,storage);
				foundGUI.reset();
				/*
				foundGUI.reset();
				int N = DM.getNumberOfFeatures();
				for( int i = 0; i < N; i++ ) {
					Point2D_F64 p = DM.getLocation(i);
					double radius = DM.getRadius(i);
					foundGUI.grow().set(p.x, p.y, radius);
				}
				*/
				foundGUI.addAll(DM.getInterestPoints());
			}
		}

		@Override
		protected void render(Canvas canvas, double imageToOutput) {
			canvas.drawBitmap(bitmap,0,0,null);

			for( int i = 0; i < foundGUI.size(); i++ ) {
				ScalePoint p = foundGUI.get(i);
				canvas.drawCircle((float)p.x,(float)p.y,(float)p.scale,paintMax);
			}
		}
	}
}