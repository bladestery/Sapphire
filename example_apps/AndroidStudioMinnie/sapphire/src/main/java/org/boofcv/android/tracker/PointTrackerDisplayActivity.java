package org.boofcv.android.tracker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.boofcv.android.DemoManager;
import org.boofcv.android.DemoVideoDisplayActivity;
import org.ddogleg.struct.FastQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.feature.tracker.PointTracker;
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
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.android.ConvertBitmap;
import boofcv.android.gui.VideoRenderProcessing;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import sapphire.oms.OMSServer;

/**
 * Base tracks for point tracker display activities.
 *
 * @author Peter Abeles
 */
public class PointTrackerDisplayActivity extends DemoVideoDisplayActivity {
	Paint paintLine = new Paint();
	Paint paintRed = new Paint();
	Paint paintBlue = new Paint();

	OMSServer server;
	DemoManager dm;
	ImageType IT = new ImageType();

	public PointTrackerDisplayActivity() {
		paintLine.setColor(Color.RED);
		paintLine.setStrokeWidth(1.5f);
		paintRed.setColor(Color.MAGENTA);
		paintRed.setStyle(Paint.Style.FILL);
		paintBlue.setColor(Color.BLUE);
		paintBlue.setStyle(Paint.Style.FILL);
	}

	protected class PointProcessing extends VideoRenderProcessing<GrayU8> {
		//PointTracker<GrayU8> tracker;

		//long tick;

		Bitmap bitmap;
		byte[] storage;

		//List<PointTrack> active = new ArrayList<PointTrack>();
		//List<PointTrack> spawned = new ArrayList<PointTrack>();
		//List<PointTrack> inactive = new ArrayList<PointTrack>();
		trackList tracklist;
		//List<PointTrack> inactive;

		// storage for data structures that are displayed in the GUI
		FastQueue<Point2D_F64> trackSrc = new FastQueue<Point2D_F64>(Point2D_F64.class,true);
		FastQueue<Point2D_F64> trackDst = new FastQueue<Point2D_F64>(Point2D_F64.class,true);
		FastQueue<Point2D_F64> trackSpawn = new FastQueue<Point2D_F64>(Point2D_F64.class,true);


		public PointProcessing() {
			super(IT.single(GrayU8.class));
			dm.initPoint();
			//this.tracker = tracker;
		}

		@Override
		protected void declareImages(int width, int height) {
			super.declareImages(width, height);
			bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
			storage = ConvertBitmap.declareStorage(bitmap, storage);
		}

		@Override
		protected void process(GrayU8 gray) {
			tracklist = dm.pointProcess(gray);
			/*
			tracker.process(gray, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW);

			// drop tracks which are no longer being used
			inactive.clear();
			tracker.getInactiveTracks(inactive);
			for( int i = 0; i < inactive.size(); i++ ) {
				PointTrack t = inactive.get(i);
				trackInfo info = t.getCookie();
				if( tick - info.lastActive > 2 ) {
					tracker.dropTrack(t);
				}
			}

			active.clear();
			tracker.getActiveTracks(active);
			for( int i = 0; i < active.size(); i++ ) {
				PointTrack t = active.get(i);
				trackInfo info = t.getCookie();
				info.lastActive = tick;
			}

			spawned.clear();
			if( active.size() < 50 )  {
				tracker.spawnTracks(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW);

				// update the track's initial position
				for( int i = 0; i < active.size(); i++ ) {
					PointTrack t = active.get(i);
					trackInfo info = t.getCookie();
					info.spawn.set(t);
				}

				tracker.getNewTracks(spawned);
				for( int i = 0; i < spawned.size(); i++ ) {
					PointTrack t = spawned.get(i);
					if( t.cookie == null ) {
						t.cookie = new trackInfo();
					}
					trackInfo info = t.getCookie();
					info.lastActive = tick;
					info.spawn.set(t);
				}
			}
			*/

			synchronized ( lockGui ) {
				ConvertBitmap.grayToBitmap(gray,bitmap,storage);

				trackSrc.reset();
				trackDst.reset();
				trackSpawn.reset();

				for( int i = 0; i < tracklist.active.size(); i++ ) {
					PointTrack t = tracklist.active.get(i);
					trackInfo info = t.getCookie();
					Point2D_F64 s = info.spawn;
					Point2D_F64 p = tracklist.active.get(i);

					trackSrc.grow().set(s);
					trackDst.grow().set(p);
				}

				for( int i = 0; i < tracklist.spawned.size(); i++ ) {
					Point2D_F64 p = tracklist.spawned.get(i);
					trackSpawn.grow().set(p);
				}
			}

			//tick++;
		}

		@Override
		protected void render(Canvas canvas, double imageToOutput) {
			canvas.drawBitmap(bitmap,0,0,null);

			for( int i = 0; i < trackSrc.size(); i++ ) {
				Point2D_F64 s = trackSrc.get(i);
				Point2D_F64 p = trackDst.get(i);
				canvas.drawLine((float)s.x,(float)s.y,(float)p.x,(float)p.y,paintLine);
				canvas.drawCircle((float)p.x,(float)p.y,2f, paintRed);
			}

			for( int i = 0; i < trackSpawn.size(); i++ ) {
				Point2D_F64 p = trackSpawn.get(i);
				canvas.drawCircle((int)p.x,(int)p.y,3, paintBlue);
			}
		}
	}
	/*
	private static class TrackInfo implements Serializable {
		long lastActive;
		Point2D_F64 spawn = new Point2D_F64();
	}
	*/
}