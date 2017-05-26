package org.boofcv.android.tracker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
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

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.tracker.ConfigComaniciu2003;
import boofcv.abst.tracker.ConfigTld;
import boofcv.abst.tracker.MeanShiftLikelihoodType;
import boofcv.abst.tracker.TrackerObjectQuad;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.tracker.sfot.SfotConfig;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoImageProcessing;
import boofcv.core.image.ConvertImage;
import boofcv.factory.tracker.FactoryTrackerObjectQuad;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I32;
import georegression.struct.shapes.Quadrilateral_F64;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Allow the user to select an object in the image and then track it
 *
 * @author Peter Abeles
 */
public class ObjectTrackerActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener, View.OnTouchListener
{
	Spinner spinnerView;

	int mode = 0;

	// size of the minimum square which the user can select
	final static int MINIMUM_MOTION = 20;

	Point2D_I32 click0 = new Point2D_I32();
	Point2D_I32 click1 = new Point2D_I32();

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();
	ConvertImage CI = new ConvertImage();
	InputSanityCheck ISC = new InputSanityCheck();

	public ObjectTrackerActivity() {
		super(true);
	}

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
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.objecttrack_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		FrameLayout iv = getViewPreview();
		iv.setOnTouchListener(this);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_algs);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.tracking_objects, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		startObjectTracking(spinnerView.getSelectedItemPosition());
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		startObjectTracking(pos);
	}

	private void startObjectTracking(int pos) {
		//TrackerObjectQuad tracker = null;
		ImageType imageType = null;

		switch (pos) {
			case 0:
				imageType = IT.single(GrayU8.class);
				dm.circulant(null);
				break;

			case 1:
				imageType = IT.pl(3, GrayU8.class);
				dm.meanShiftComaniciu2003(new ConfigComaniciu2003(false), imageType);
				break;

			case 2:
				imageType = IT.pl(3, GrayU8.class);
				dm.meanShiftComaniciu2003(new ConfigComaniciu2003(true), imageType);
				break;

			case 3:
				imageType = IT.pl(3, GrayU8.class);
				dm.meanShiftLikelihood(30,5,256, MeanShiftLikelihoodType.HISTOGRAM, imageType);
				break;

			case 4:{
				imageType = IT.single(GrayU8.class);
				SfotConfig config = new SfotConfig();
				config.numberOfSamples = 10;
				config.robustMaxError = 30;
				dm.sparseFlow(config);
			}break;

			case 5:
				imageType = IT.single(GrayU8.class);
				dm.tld(new ConfigTld(false));
				break;

			default:
				throw new RuntimeException("Unknown tracker: "+pos);
		}
		setProcessing(new TrackingProcessing(imageType));
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if( mode == 0 ) {
			if(MotionEvent.ACTION_DOWN == motionEvent.getActionMasked()) {
				click0.set((int) motionEvent.getX(), (int) motionEvent.getY());
				click1.set((int) motionEvent.getX(), (int) motionEvent.getY());
				mode = 1;
			}
		} else if( mode == 1 ) {
			if(MotionEvent.ACTION_MOVE == motionEvent.getActionMasked()) {
				click1.set((int)motionEvent.getX(),(int)motionEvent.getY());
			} else if(MotionEvent.ACTION_UP == motionEvent.getActionMasked()) {
				click1.set((int)motionEvent.getX(),(int)motionEvent.getY());
				mode = 2;
			}
		}
		return true;
	}

	public void resetPressed( View view ) {
		mode = 0;
	}

	protected class TrackingProcessing<T extends ImageBase> extends VideoImageProcessing<Planar<GrayU8>>
	{
		T input;
		ImageType<T> inputType;

		//TrackerObjectQuad tracker;
		boolean visible;

		Quadrilateral_F64 location = new Quadrilateral_F64();

		Paint paintSelected = new Paint();
		Paint paintLine0 = new Paint();
		Paint paintLine1 = new Paint();
		Paint paintLine2 = new Paint();
		Paint paintLine3 = new Paint();
		private Paint textPaint = new Paint();

		protected TrackingProcessing(ImageType<T> inputType) {
			super(IT.pl(3, GrayU8.class));
			this.inputType = inputType;

			if( inputType.getFamily() == ImageType.Family.GRAY ) {
				input = inputType.createImage(1,1);
			}

			mode = 0;
			//this.tracker = tracker;

			paintSelected.setColor(Color.argb(0xFF/2,0xFF,0,0));

			paintLine0.setColor(Color.RED);
			paintLine0.setStrokeWidth(3f);
			paintLine1.setColor(Color.MAGENTA);
			paintLine1.setStrokeWidth(3f);
			paintLine2.setColor(Color.BLUE);
			paintLine2.setStrokeWidth(3f);
			paintLine3.setColor(Color.GREEN);
			paintLine3.setStrokeWidth(3f);

			// Create out paint to use for drawing
			textPaint.setARGB(255, 200, 0, 0);
			textPaint.setTextSize(60);

		}

		@Override
		protected void process(Planar<GrayU8> input, Bitmap output, byte[] storage)
		{
			updateTracker(input);
			visualize(input, output, storage);
		}

		private void updateTracker(Planar<GrayU8> color) {
			if( inputType.getFamily() == ImageType.Family.GRAY ) {
				input.reshape(color.width,color.height);
				//input = (T) dm.convertAverage(color);
				CI.average(color,(GrayU8)input, ISC);
			} else {
				input = (T)color;
			}

			if( mode == 2 ) {
				imageToOutput(click0.x, click0.y, location.a);
				imageToOutput(click1.x, click1.y, location.c);

				// make sure the user selected a valid region
				makeInBounds(location.a);
				makeInBounds(location.c);

				if( movedSignificantly(location.a,location.c) ) {
					// use the selected region and start the tracker
					location.b.set(location.c.x, location.a.y);
					location.d.set( location.a.x, location.c.y );

					location = dm.trackerMoved(input, location);
					visible = true;
					mode = 3;
				} else {
					// the user screw up. Let them know what they did wrong
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(ObjectTrackerActivity.this, "Drag a larger region", Toast.LENGTH_SHORT).show();
						}
					});
					mode = 0;
				}
			} else if( mode == 3 ) {
				track Track = dm.trackerProcess(input, location);
				visible = Track.visible;
				location = Track.location;
			}
		}

		private void visualize(Planar<GrayU8> color, Bitmap output, byte[] storage) {
			ConvertBitmap.multiToBitmap(color, output, storage);
			Canvas canvas = new Canvas(output);

			if( mode == 1 ) {
				Point2D_F64 a = new Point2D_F64();
				Point2D_F64 b = new Point2D_F64();

				imageToOutput(click0.x, click0.y, a);
				imageToOutput(click1.x, click1.y, b);

				canvas.drawRect((int)a.x,(int)a.y,(int)b.x,(int)b.y,paintSelected);
			} else if( mode >= 2 ) {
				if( visible ) {
					Quadrilateral_F64 q = location;

					drawLine(canvas,q.a,q.b,paintLine0);
					drawLine(canvas,q.b,q.c,paintLine1);
					drawLine(canvas,q.c,q.d,paintLine2);
					drawLine(canvas,q.d,q.a,paintLine3);
				} else {
					canvas.drawText("?",color.width/2,color.height/2,textPaint);
				}
			}
		}

		private void drawLine( Canvas canvas , Point2D_F64 a , Point2D_F64 b , Paint color ) {
			canvas.drawLine((float)a.x,(float)a.y,(float)b.x,(float)b.y,color);
		}

		private void makeInBounds( Point2D_F64 p ) {
			if( p.x < 0 ) p.x = 0;
			else if( p.x >= input.width )
				p.x = input.width - 1;

			if( p.y < 0 ) p.y = 0;
			else if( p.y >= input.height )
				p.y = input.height - 1;

		}

		private boolean movedSignificantly( Point2D_F64 a , Point2D_F64 b ) {
			if( Math.abs(a.x-b.x) < MINIMUM_MOTION )
				return false;
			if( Math.abs(a.y-b.y) < MINIMUM_MOTION )
				return false;

			return true;
		}
	}
}