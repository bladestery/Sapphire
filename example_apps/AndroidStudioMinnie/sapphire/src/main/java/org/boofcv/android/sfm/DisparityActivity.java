package org.boofcv.android.sfm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import org.boofcv.android.DemoMain;
import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;
import org.boofcv.android.assoc.AssociationVisualize;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.disparity.StereoDisparity;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.android.ConvertBitmap;
import boofcv.android.VisualizeImageData;
import boofcv.android.gui.VideoRenderProcessing;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs;
import boofcv.factory.feature.disparity.DisparityAlgorithms;
import boofcv.factory.feature.disparity.FactoryStereoDisparity;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static org.boofcv.android.DemoMain.client;
import static org.boofcv.android.DemoMain.edge;
import static org.boofcv.android.DemoMain.preference;
import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Computes the stereo disparity between two images captured by the camera.  The user selects the images and which
 * algorithm to process them using.
 *
 * @author Peter Abeles
 */
public class DisparityActivity extends DemoVideoDisplayActivity
		implements AdapterView.OnItemSelectedListener
{
	Spinner spinnerView;
	Spinner spinnerAlgs;

	AssociationVisualize visualize;

	// indicate where the user touched the screen
	volatile int touchEventType = 0;
	volatile int touchX;
	volatile int touchY;
	volatile boolean reset = false;

	private GestureDetector mDetector;

	// used to notify processor that the disparity algorithms need to be changed
	int changeDisparityAlg = -1;

	DView activeView = DView.ASSOCIATION;

	public DisparityActivity() {
		visualize = new AssociationVisualize(this);
	}

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();
	ImageMiscOps IMO = new ImageMiscOps();

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
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.disparity_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		spinnerView = (Spinner)controls.findViewById(R.id.spinner_view);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.disparity_views, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerView.setAdapter(adapter);
		spinnerView.setOnItemSelectedListener(this);

		spinnerAlgs = (Spinner)controls.findViewById(R.id.spinner_algs);
		adapter = ArrayAdapter.createFromResource(this,
				R.array.disparity_algs, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAlgs.setAdapter(adapter);
		spinnerAlgs.setOnItemSelectedListener(this);

		FrameLayout iv = getViewPreview();
		mDetector = new GestureDetector(this, new MyGestureDetector(iv));
		iv.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				mDetector.onTouchEvent(event);
				return true;
			}});
	}

	@Override
	protected void onResume() {
		super.onResume();
		setProcessing(new DisparityProcessing());
		visualize.setSource(null);
		visualize.setDestination(null);
		changeDisparityAlg = spinnerAlgs.getSelectedItemPosition();
	}


	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id ) {
		if( adapterView == spinnerView ) {
			if( pos == 0 ) {
				activeView = DView.ASSOCIATION;
			} else if( pos == 1 ) {
				touchY = -1;
				activeView = DView.RECTIFICATION;
			} else {
				activeView = DView.DISPARITY;
			}
		} else if( adapterView == spinnerAlgs ) {
			changeDisparityAlg = pos;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {}

	public void resetPressed( View view ) {
		reset = true;
	}

	protected class MyGestureDetector extends GestureDetector.SimpleOnGestureListener
	{
		View v;

		public MyGestureDetector(View v) {
			this.v = v;
		}

		@Override
		public boolean onDown(MotionEvent e) {

			// make sure the camera is calibrated first
			if( DemoMain.preference.intrinsic == null ) {
				Toast.makeText(DisparityActivity.this, "You must first calibrate the camera!", Toast.LENGTH_SHORT).show();
				return false;
			}

			if( activeView == DView.ASSOCIATION ) {
				touchEventType = 1;
				touchX = (int)e.getX();
				touchY = (int)e.getY();
			} else if( activeView == DView.RECTIFICATION ) {
				touchY = (int)e.getY();
			}

			return true;
		}

		/**
		 * If the user flings an image discard the results in the image
		 */
		@Override
		public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if( activeView != DView.ASSOCIATION ) {
				return false;
			}

			touchEventType = (int)e1.getX() < v.getWidth()/2 ? 2 : 3;

			return true;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e)
		{
			if( activeView != DView.ASSOCIATION ) {
				return false;
			}

			touchEventType = 4;
			return true;
		}
	}


	protected class DisparityProcessing extends VideoRenderProcessing<GrayF32> {

		//DisparityCalculation<BrightFeature> disparity;

		boolean init = false;

		GrayF32 disparityImage;
		int disparityMin,disparityMax;

		public DisparityProcessing() {
			super(IT.single(GrayF32.class));
			dm.initDisparity(DemoMain.preference.intrinsic);
			/*
			DetectDescribePoint<GrayF32, BrightFeature> detDesc =
					FactoryDetectDescribe.surfFast(null,null,null,GrayF32.class, FFE, FIPA, FKG);

			ScoreAssociation<BrightFeature> score = FA.defaultScore(BrightFeature.class);
			AssociateDescription<BrightFeature> associate =
					FA.greedy(score,Double.MAX_VALUE,true);

			disparity = new DisparityCalculation<BrightFeature>(detDesc,associate,DemoMain.preference.intrinsic);
			*/
		}

		@Override
		protected void declareImages(int width, int height) {
			super.declareImages(width, height);
			dm.declDisparity(width, height);
			disparityImage = new GrayF32(width,height);

			visualize.initializeImages( width, height );
			outputWidth = visualize.getOutputWidth();
			outputHeight = visualize.getOutputHeight();

			//disparity.init(width,height);
		}

		private void createDisparity() {

			DisparityAlgorithms which;
			switch( changeDisparityAlg ) {
				case 0:
					which = DisparityAlgorithms.RECT;
					break;

				case 1:
					which = DisparityAlgorithms.RECT_FIVE;
					break;

				default:
					throw new RuntimeException("Unknown algorithm "+changeDisparityAlg);
			}


			dm.setDisparityAlg(which);
		}

		@Override
		protected void process(GrayF32 gray) {

			int target = 0;

			// process GUI interactions
			synchronized ( lockGui ) {
				if( reset ) {
					reset = false;
					visualize.setSource(null);
					visualize.setDestination(null);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							spinnerView.setSelection(0);
						}
					});

				}
				if( touchEventType == 1 ) {
					// first see if there are any features to select
					if( !visualize.setTouch(touchX,touchY) ) {
						// if not then it must be a capture image request
						target = touchX < view.getWidth()/2 ? 1 : 2;
					}
				} else if( touchEventType == 2 ) {
					visualize.setSource(null);
					visualize.forgetSelection();
				} else if( touchEventType == 3 ) {
					visualize.setDestination(null);
					visualize.forgetSelection();
				} else if( touchEventType == 4 ) {
					visualize.forgetSelection();
				}
			}
			touchEventType = 0;

			boolean computedFeatures = false;
			// compute image features for left or right depending on user selection
			if( target == 1 ) {
				setProgressMessage("Detecting Features Left");
				dm.setSource(gray);
				//disparity.setSource(gray);
				computedFeatures = true;
			} else if( target == 2 ) {
				setProgressMessage("Detecting Features Right");
				dm.setDestination(gray);
				//disparity.setDestination(gray);
				computedFeatures = true;
			}

			synchronized ( lockGui ) {
				if( target == 1 ) {
					visualize.setSource(gray);
				} else if( target == 2 ) {
					visualize.setDestination(gray);
				}
			}

			if( changeDisparityAlg != -1 ) {
				createDisparity();
				init = true;
				//disparity.setDisparityAlg(createDisparity());
			}

			if( init ) {
				if( computedFeatures && visualize.hasLeft && visualize.hasRight ) {
					// rectify the images and compute the disparity
					setProgressMessage("Rectifying");
					//boolean success = disparity.rectifyImage();
					boolean success = dm.rectify();
					if( success ) {
						setProgressMessage("Disparity");
						disparities disparity = dm.computeDisparity();
						//disparity.computeDisparity();
						synchronized ( lockGui ) {
							disparityMin = disparity.disparityMin;
							disparityMax = disparity.disparityMax;

							disparityImage.setTo(disparity.disparity);
							visualize.setMatches(disparity.Inliers);
							visualize.forgetSelection();

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									spinnerView.setSelection(2); // switch to disparity view
								}
							});
						}
					} else {
						synchronized ( lockGui ) {
							IMO.fill(disparityImage,0);
						}
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(DisparityActivity.this, "Disparity computation failed!", Toast.LENGTH_SHORT).show();
							}});
					}
				} else if( changeDisparityAlg != -1 && visualize.hasLeft && visualize.hasRight ) {
					// recycle the rectified image but compute the disparity using the new algorithm
					setProgressMessage("Disparity");
					disparities disparity = dm.computeDisparity();
					//disparity.computeDisparity();

					synchronized ( lockGui ) {
						disparityMin = disparity.disparityMin;
						disparityMax = disparity.disparityMax;
						disparityImage.setTo(disparity.disparity);
					}
				}
			}

			hideProgressDialog();
			changeDisparityAlg = -1;
		}

		@Override
		protected void render(Canvas canvas, double imageToOutput) {
			if( DemoMain.preference.intrinsic == null ) {
				canvas.restore();
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				paint.setTextSize(60);
				int textLength = (int)paint.measureText("Calibrate Camera First");

				canvas.drawText("Calibrate Camera First", (canvas.getWidth() - textLength) / 2, canvas.getHeight() / 2, paint);
			} else if( activeView == DView.DISPARITY ) {
				// draw rectified image
				ConvertBitmap.grayToBitmap(dm.getLeft(), visualize.bitmapSrc, visualize.storage);
				canvas.drawBitmap(visualize.bitmapSrc,0,0,null);

				if(dm.isAvailable() ) {
					VisualizeImageData.disparity(disparityImage,disparityMin,disparityMax,0,
							visualize.bitmapDst,visualize.storage);

					int startX = disparityImage.getWidth() + AssociationVisualize.SEPARATION;
					canvas.drawBitmap(visualize.bitmapDst,startX,0,null);
				}
			} else if( activeView == DView.RECTIFICATION ) {
				leftright disp = dm.getleftright();
				ConvertBitmap.grayToBitmap(disp.Left,visualize.bitmapSrc,visualize.storage);
				ConvertBitmap.grayToBitmap(disp.Right,visualize.bitmapDst,visualize.storage);

				int startX = disp.Left.getWidth() + AssociationVisualize.SEPARATION;
				canvas.drawBitmap(visualize.bitmapSrc,0,0,null);
				canvas.drawBitmap(visualize.bitmapDst,startX,0,null);

				if( touchY >= 0 ) {
					canvas.restore();
					canvas.drawLine(0,touchY,canvas.getWidth(),touchY,visualize.paintPoint);
				}
			} else {
				// bit of a hack to reduce memory usage
				ConvertBitmap.grayToBitmap(visualize.graySrc,visualize.bitmapSrc,visualize.storage);
				ConvertBitmap.grayToBitmap(visualize.grayDst,visualize.bitmapDst,visualize.storage);

				visualize.render(canvas,tranX,tranY,scale);
			}
		}
	}

	enum DView {
		ASSOCIATION,
		RECTIFICATION,
		DISPARITY
	}
}