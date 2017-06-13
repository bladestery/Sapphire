package org.boofcv.android.sfm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.boofcv.android.R;
import org.ddogleg.struct.FastQueue;

import java.net.InetSocketAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.abst.sfm.AccessPointTracks;
import boofcv.abst.sfm.d2.ImageMotion2D;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
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
import boofcv.alg.sfm.d2.StitchingFromMotion2D;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoRenderProcessing;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.sfm.FactoryMotion2D;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.misc.BoofMiscOps;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.struct.affine.Affine2D_F64;
import georegression.struct.homography.Homography2D_F64;
import georegression.struct.point.Point2D_F64;
import georegression.transform.homography.HomographyPointOps_F64;
import sapphire.kernel.server.KernelServerImpl;
import sapphire.oms.OMSServer;

import static org.boofcv.android.DemoMain.client;
import static org.boofcv.android.DemoMain.edge;
import static sapphire.kernel.common.GlobalKernelReferences.nodeServer;

/**
 * Displays an image mosaic created from the video stream.
 *
 * @author Peter Abeles
 */
public class MosaicDisplayActivity extends DemoVideoDisplayActivity
implements CompoundButton.OnCheckedChangeListener
{
	Paint paintInlier;
	Paint paintOutlier;

	boolean showFeatures;
	boolean resetRequested;
	boolean paused = false;

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		paintInlier = new Paint();
		paintInlier.setColor(Color.RED);
		paintInlier.setStyle(Paint.Style.FILL);
		paintOutlier = new Paint();
		paintOutlier.setColor(Color.BLUE);
		paintOutlier.setStyle(Paint.Style.FILL);

		resetRequested = false;

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
		LinearLayout controls = (LinearLayout)inflater.inflate(R.layout.mosaic_controls,null);

		LinearLayout parent = getViewContent();
		parent.addView(controls);

		CheckBox seek = (CheckBox)controls.findViewById(R.id.check_features);
		seek.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		createStabilization();
		setProcessing(new PointProcessing());
	}

	public void resetPressed( View view ) {
		resetRequested = true;
	}

	public void pausedPressed( View view ) {
		paused = !((ToggleButton)view).isChecked();
	}

	private void createStabilization() {

		ConfigGeneralDetector config = new ConfigGeneralDetector();
		config.maxFeatures = 150;
		config.threshold = 40;
		config.radius = 3;

		dm.createStabilization(config);
		/*
		PointTracker<GrayU8> tracker = FPT.
				klt(new int[]{1, 2,4}, config, 3, GrayU8.class, GrayS16.class, FD, GIO, FIB, FKG, FP, FFE, FIPA, FI);

		ImageMotion2D<GrayU8,Affine2D_F64> motion = FactoryMotion2D.createMotion2D(100, 1.5, 2, 40,
				0.5, 0.6, false,tracker, new Affine2D_F64());

		return FactoryMotion2D.createVideoStitch(0.2,motion, IT.single(GrayU8.class), FIB, FI, FDs);
		*/
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		showFeatures = b;
	}

	protected class PointProcessing extends VideoRenderProcessing<GrayU8> {
		//StitchingFromMotion2D<GrayU8,Affine2D_F64> alg;

		Bitmap bitmap;
		byte[] storage;

		StitchingFromMotion2D.Corners corners = new StitchingFromMotion2D.Corners();

		FastQueue<Point2D_F64> inliersGui = new FastQueue<Point2D_F64>(Point2D_F64.class,true);
		FastQueue<Point2D_F64> outliersGui = new FastQueue<Point2D_F64>(Point2D_F64.class,true);

		public PointProcessing( ) {
			super(IT.single(GrayU8.class));
			//this.alg = alg;
		}

		@Override
		protected void declareImages(int width, int height) {
			super.declareImages(width, height);

			outputWidth = width*2;
			outputHeight = height;
			/*
			int tx = outputWidth/2 - width/4;
			int ty = outputHeight/2 - height/4;

			Affine2D_F64 init = new Affine2D_F64(0.5,0,0,0.5,tx,ty);
			init = init.invert(null);
			*/
			dm.declMosaic(width, height);

			//alg.configure(outputWidth,outputHeight,init);
			bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
			storage = ConvertBitmap.declareStorage(bitmap, storage);
		}

		@Override
		protected void process(GrayU8 gray) {
			if(paused)
				return;

			if( !resetRequested) {
				GrayU8 stitched = dm.mosaicProcess(gray);
				if (stitched != null) {
					mosaic ret;
					synchronized ( lockGui ) {
						ConvertBitmap.grayToBitmap(stitched,bitmap,storage);

						ret = dm.updateGUI(showFeatures,gray, true);
						/*
						ImageMotion2D<?,?> motion = alg.getMotion();
						if( showFeatures && (motion instanceof AccessPointTracks) ) {
							AccessPointTracks access = (AccessPointTracks)motion;

							alg.getWorldToCurr(imageToDistorted);
							imageToDistorted.invert(distortedToImage);
							inliersGui.reset();outliersGui.reset();
							List<Point2D_F64> points = access.getAllTracks();
							for( int i = 0; i < points.size(); i++ ) {
								HomographyPointOps_F64.transform(distortedToImage,points.get(i),distPt);

								if( access.isInlier(i) ) {
									inliersGui.grow().set(distPt.x,distPt.y);
								} else {
									outliersGui.grow().set(distPt.x,distPt.y);
								}
							}
						}

						alg.getImageCorners(gray.width,gray.height,corners);
						*/
						corners = ret.corners;
						inliersGui = ret.inliersGui;
						outliersGui = ret.outliersGui;
					}

					/*
					boolean inside = true;
					inside &= BoofMiscOps.checkInside(stitched,corners.p0.x,corners.p0.y,5);
					inside &= BoofMiscOps.checkInside(stitched,corners.p1.x,corners.p1.y,5);
					inside &= BoofMiscOps.checkInside(stitched,corners.p2.x,corners.p2.y,5);
					inside &= BoofMiscOps.checkInside(stitched,corners.p3.x,corners.p3.y,5);
					if( !inside ) {
						alg.setOriginToCurrent(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
								GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
					}
					*/
				}
			} else {
				resetRequested = false;
				dm.mosaicReset();
			}
		}

		@Override
		protected void render(Canvas canvas, double imageToOutput) {
			canvas.drawBitmap(bitmap,0,0,null);

			Point2D_F64 p0 = corners.p0;
			Point2D_F64 p1 = corners.p1;
			Point2D_F64 p2 = corners.p2;
			Point2D_F64 p3 = corners.p3;

			canvas.drawLine((int)p0.x,(int)p0.y,(int)p1.x,(int)p1.y, paintInlier);
			canvas.drawLine((int)p1.x,(int)p1.y,(int)p2.x,(int)p2.y, paintInlier);
			canvas.drawLine((int)p2.x,(int)p2.y,(int)p3.x,(int)p3.y, paintInlier);
			canvas.drawLine((int)p3.x,(int)p3.y,(int)p0.x,(int)p0.y, paintInlier);

			for( int i = 0; i < inliersGui.size; i++ ) {
				Point2D_F64 p = inliersGui.get(i);
				canvas.drawCircle((float)p.x,(float)p.y,3,paintInlier);
			}
			for( int i = 0; i < outliersGui.size; i++ ) {
				Point2D_F64 p = outliersGui.get(i);
				canvas.drawCircle((float)p.x,(float)p.y,3,paintOutlier);
			}
		}
	}
}