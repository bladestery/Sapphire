package org.boofcv.android.detect;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;
import org.ddogleg.struct.FastQueue;
import org.ddogleg.struct.GrowQueue_I32;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.segmentation.ImageSuperpixels;
import boofcv.alg.segmentation.ComputeRegionMeanColor;
import boofcv.alg.segmentation.ImageSegmentationOps;
import boofcv.android.ConvertBitmap;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.factory.segmentation.ConfigSlic;
import boofcv.factory.segmentation.FactoryImageSegmentation;
import boofcv.factory.segmentation.FactorySegmentationAlg;
import boofcv.struct.feature.ColorQueue_F32;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Displays the results of image segmentation after the user clicks on an image
 *
 * @author Peter Abeles
 */
public class SegmentationDisplayActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	Spinner spinnerView;

	Mode mode = Mode.VIEW_VIDEO;
	boolean hasSegment = false;

	private GestureDetector mDetector;

	OMSServer server;
	DemoManager dm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			dm = (DemoManager) server.getAppEntryPoint();
			System.out.println("Got AppEntryPoint");
			//dm.LatencyCheck();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		LayoutInflater inflater = getLayoutInflater();
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.select_algorithm,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.segmentation_algs, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);

		FrameLayout iv = getViewPreview();
		mDetector = new GestureDetector(this, new MyGestureDetector(iv));
		iv.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				mDetector.onTouchEvent(event);
				return true;
			}});

		//Toast.makeText(this,"FAST DEVICES ONLY! Can take minutes.",Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		startSegmentProcess(spinnerView.getSelectedItemPosition());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		startSegmentProcess(pos);
	}

	private void startSegmentProcess(int pos) {

		mode = Mode.VIEW_VIDEO;
		hasSegment = false;

		//ImageType<Planar<GrayU8>> type = IT.pl(3, GrayU8.class);
		//dm.pL(3);

		switch (pos) {
			case 0:
				dm.watershed(3, null);
				break;

			case 1:
				dm.fh04(3, null);
				break;

			case 2:
				dm.slic(3, new ConfigSlic(100));
				break;

			case 3:
				dm.meanShift(3, null);
				break;
		}
		setProcessing(new SegmentationProcessing());
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	protected class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
		View v;

		public MyGestureDetector(View v) {
			this.v = v;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// toggle just displaying the video feed versus a segmented image
			switch( mode ) {
				case VIEW_VIDEO:mode = Mode.VIEW_MEAN;break;
				case VIEW_MEAN:mode = Mode.VIEW_LINES;break;
				case VIEW_LINES:mode = Mode.VIEW_VIDEO;break;
			}
			if( mode == Mode.VIEW_MEAN ) {
				hasSegment = false;
			}
			return true;
		}
	}

	protected class SegmentationProcessing extends VideoImageProcessing<Planar<GrayU8>> {
		GrayS32 pixelToRegion;
		//ImageSuperpixels<Planar<GrayU8>> segmentation;
		//Planar<GrayU8> background;

		//ComputeRegionMeanColor colorize;
		FastQueue<float[]> segmentColor;
		//GrowQueue_I32 regionMemberCount = new GrowQueue_I32();

		public SegmentationProcessing() {
			super(dm.pl(3));
			//this.segmentation = segmentation;
			//this.colorize = FactorySegmentationAlg.regionMeanColor(segmentation.getImageType());
		}

		@Override
		protected void declareImages( int width , int height ) {
			super.declareImages(width, height);
			dm.initImage(width, height);
			//pixelToRegion = new GrayS32(width,height);
			//background = new Planar<GrayU8>(GrayU8.class,width,height,3);
		}

		@Override
		protected void process(Planar<GrayU8> input, Bitmap output, byte[] storage) {

			// TODO if the user tries to exit while computing a segmentation things get weird
			if( mode != Mode.VIEW_VIDEO ) {
				if( !hasSegment ) {
					// save the current image
					//background.setTo(input);
					hasSegment = true;
					setProgressMessage("Slowly Segmenting");
					//segmentation.segment(input, pixelToRegion);
					pixelToRegion = dm.segment(input);

					// Computes the mean color inside each region
					//ComputeRegionMeanColor colorize = FactorySegmentationAlg.regionMeanColor(input.getImageType());

					//int numSegments = segmentation.getTotalSuperpixels();

					//segmentColor.resize(numSegments);
					//regionMemberCount.resize(numSegments);

					//ImageSegmentationOps.countRegionPixels(pixelToRegion, numSegments, regionMemberCount.data);
					//colorize.process(background,pixelToRegion,regionMemberCount,segmentColor);
					segmentColor = dm.colorize(input);

					hideProgressDialog();
				}
				VisualizeImageData.regionsColor(pixelToRegion, segmentColor, output, storage);
				if( mode == Mode.VIEW_LINES ) {
					VisualizeImageData.regionBorders(pixelToRegion, 0xFF0000, output, storage);
				}
			} else {
				ConvertBitmap.multiToBitmap(input,output,storage);
			}
		}
	}

	static enum Mode {
		VIEW_VIDEO,
		VIEW_MEAN,
		VIEW_LINES
	}
}