package org.boofcv.android.calib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.boofcv.android.DemoMain;
import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.distort.AdjustmentType;
import boofcv.alg.distort.ImageDistort;
import boofcv.alg.distort.LensDistortionOps;
import boofcv.alg.distort.PointToPixelTransform_F32;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.interpolate.InterpolatePixelS;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.distort.Point2Transform2_F32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static org.boofcv.android.DemoMain.client;
import static org.boofcv.android.DemoMain.edge;
import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * After the camera has been calibrated the user can display a distortion free image
 *
 * @author Peter Abeles
 */
public class UndistortDisplayActivity extends DemoVideoDisplayActivity
		implements CompoundButton.OnCheckedChangeListener
{
	ToggleButton toggleDistort;
	ToggleButton toggleColor;

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();

	boolean isDistorted = false;
	boolean isColor = false;

	//ImageDistort<GrayU8,GrayU8> removeDistortion;

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
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.undistort_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		toggleDistort = (ToggleButton)controls.findViewById(R.id.toggle_distort);
		toggleDistort.setOnCheckedChangeListener(this);
		toggleDistort.setChecked(isDistorted);

		toggleColor = (ToggleButton)controls.findViewById(R.id.toggle_color);
		toggleColor.setOnCheckedChangeListener(this);
		toggleColor.setChecked(isColor);

		if( DemoMain.preference.intrinsic != null ) {
			LensDistortionOps LDO = new LensDistortionOps();
			Point2Transform2_F32 fullView = LDO.transform_F32(AdjustmentType.FULL_VIEW,
					DemoMain.preference.intrinsic, null, false);

			dm.initDistort(fullView);
			/*
			// define the transform.  Cache the results for quick rendering later on
			Point2Transform2_F32 fullView = LensDistortionOps.transform_F32(AdjustmentType.FULL_VIEW,
					DemoMain.preference.intrinsic, null, false);
			InterpolatePixelS<GrayU8> interp = FactoryInterpolation.
					bilinearPixelS(GrayU8.class, BorderType.ZERO, FIB);
			// for some reason not caching is faster on a low end phone.  Maybe it has to do with CPU memory
			// cache misses when looking up a point?
			removeDistortion = FactoryDistort.distortSB(false,interp,GrayU8.class);
			removeDistortion.setModel(new PointToPixelTransform_F32(fullView));
			*/
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setProcessing(new UndistortProcessing());
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		if( DemoMain.preference.intrinsic == null ) {
			Toast toast = Toast.makeText(UndistortDisplayActivity.this,
					"You must first calibrate the camera!", Toast.LENGTH_LONG);
			toast.show();
		}
		if( toggleDistort == compoundButton ) {
			isDistorted = b;
		} else if( toggleColor == compoundButton ) {
			isColor = b;
		}
	}

	protected class UndistortProcessing extends VideoImageProcessing<Planar<GrayU8>> {
		Planar<GrayU8> undistorted;
		GrayU8 out;

		public UndistortProcessing() {
			super(IT.pl(3,GrayU8.class));
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.declDistort(width, height);
		}

		@Override
		protected void process(Planar<GrayU8> input, Bitmap output, byte[] storage) {
			if( DemoMain.preference.intrinsic == null ) {
				Canvas canvas = new Canvas(output);
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				paint.setTextSize(output.getWidth()/10);
				int textLength = (int)paint.measureText("Calibrate Camera First");

				canvas.drawText("Calibrate Camera First", (canvas.getWidth() - textLength) / 2, canvas.getHeight() / 2, paint);
			} else if( isDistorted ) {
				if( isColor )
					ConvertBitmap.multiToBitmap(input,output,storage);
				else {
					out = dm.undis(input);
					//CI.average(input,undistorted.getBand(0), ISC);
					ConvertBitmap.grayToBitmap(out,output,storage);
				}
			} else {
				if( isColor ) {
					/*
					for( int i = 0; i < input.getNumBands(); i++ ) {
						removeDistortion.apply(input.getBand(i),undistorted.getBand(i));
					}*/
					undistorted = dm.colDistort(input);

					ConvertBitmap.multiToBitmap(undistorted,output,storage);
				} else {
					//undistorted = dm.undis(input, undistorted);
					//CI.average(input,undistorted.getBand(0), ISC);
					out = dm.remDistort(input);
					//removeDistortion.apply(undistorted.getBand(0),undistorted.getBand(1));
					ConvertBitmap.grayToBitmap(out,output,storage);
				}
			}
		}
	}
}