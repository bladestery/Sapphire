package org.boofcv.android.ip;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import java.awt.Image;
import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.filter.blur.BlurFilter;
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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static org.boofcv.android.DemoMain.client;
import static org.boofcv.android.DemoMain.edge;
import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Blurs the input video image using different algorithms.
 *
 * @author Peter Abeles
 */
public class BlurDisplayActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	Spinner spinnerView;

	// amount of blur applied to the image
	int radius;

	BlurProcessing processing;

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		InetSocketAddress host, omsHost;

		try {
			Registry registry = LocateRegistry.getRegistry(edge, 22346);
			server = (OMSServer) registry.lookup("SapphireOMS");
			System.out.println(server);

			host = new InetSocketAddress(client, 22346);
			omsHost = new InetSocketAddress(edge, 22346);
			nodeServer = new KernelServerImpl(host, omsHost);
			System.out.println(nodeServer);

			System.setProperty("java.rmi.server.hostname", host.getAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			dm = (DemoManager) server.getAppEntryPoint();
			//dm.LatencyCheck();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.blur_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.blurs, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);

		SeekBar seek = (SeekBar)controls.findViewById(R.id.slider_radius);
		radius = seek.getProgress();

		seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				radius = progress;
				if( radius > 0 )
					processing.setRadius(radius);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		startBlurProcess(spinnerView.getSelectedItemPosition());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		startBlurProcess(pos);
	}

	private void startBlurProcess(int pos) {
		// not sure what these do if the radius is set to 0
		int radius = Math.max(1,this.radius);
		switch (pos) {
			case 0:
				dm.mean(radius);
				//FBF.mean(GrayU8.class, radius, GIO);
				processing = new BlurProcessing();
				break;

			case 1:
				dm.gaussian(-1, radius);
				//FBF.gaussian(GrayU8.class,-1,radius, GIO);
				processing = new BlurProcessing();
				break;

			case 2:
				dm.blurMedian(radius);
				//FBF.median(GrayU8.class,radius, GIO);
				processing = new BlurProcessing();
				break;
		}

		setProcessing(processing);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	protected class BlurProcessing extends VideoImageProcessing<GrayU8> {
		GrayU8 blurred;
		//final BlurFilter<GrayU8> filter;

		public BlurProcessing() {
			super(IT.single(GrayU8.class));
			//this.filter = filter;
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initBlurred(width,height);
			//blurred = new GrayU8(width,height);
		}

		@Override
		protected void process(GrayU8 input, Bitmap output, byte[] storage) {
			if( radius > 0 ) {
				//synchronized ( filter ) {
				//	blurred = dm.blurProcess(input);
					//blurred = filter.process(input, blurred, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG);
				//}
				blurred = dm.blurProcess(input);
				ConvertBitmap.grayToBitmap(blurred, output, storage);
			} else {
				ConvertBitmap.grayToBitmap(input, output, storage);
			}
		}

		public void setRadius( int radius ) {
			//synchronized ( filter ) {
			//	dm.setRadius(radius);
				//filter.setRadius(radius);
			//}
			dm.setRadius(radius);
		}
	}
}