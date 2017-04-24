package org.boofcv.android.recognition;

import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.filter.binary.InputToBinary;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.fiducial.square.BaseDetectFiducialSquare;
import boofcv.alg.fiducial.square.FoundFiducial;
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
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.shape.ConfigPolygonDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.struct.shapes.Quadrilateral_F64;

/**
 * Detects fiducials inside of an image.  Used to return the pattern inside.
 *
 * @author Peter Abeles
 */
public class FiducialDetector extends BaseDetectFiducialSquare<GrayU8> {
	private static GBlurImageOps GBIO;
	private static GeneralizedImageOps GIO;
	private static InputSanityCheck ISC;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ConvolveNormalized CN;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static FactoryThresholdBinary FTB;
	private static FactoryShapeDetector FSD;
	private static ImageType IT;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static final String TAG = "FiducialDetector";

	// Width of black border (units = pixels)
	private final static int w=32;
	private final static int squareLength=w*4; // this must be a multiple of 16

	private InputToBinary<GrayF32> threshold = FTB.globalOtsu(0,255,true,GrayF32.class, IT);
	private GrayF32 grayNoBorder = new GrayF32();

	// All the images inside which it found
	private FastQueue<GrayU8> foundBinary;

	public FiducialDetector() {
		super(FTB.globalOtsu(0, 255, true, GrayU8.class, IT), FSD.polygon(
						new ConfigPolygonDetector(false, 4, 4), GrayU8.class),
				0.25, 0.5, squareLength + squareLength, GrayU8.class);

		foundBinary = new FastQueue<GrayU8>(GrayU8.class,true) {
			@Override
			protected GrayU8 createInstance() {
				return new GrayU8(squareLength,squareLength);
			}
		};
	}

	@Override
	public void process(GrayU8 gray) {
		foundBinary.reset();
		super.process(gray);
	}

	/**
	 * Returns a list of all detected fiducials
	 */
	public List<Detected> getDetected() {
		FastQueue<FoundFiducial> found = getFound();
		if( found.size() <= 0 )
			return new ArrayList<>();

		// select the square with the largest area
		List<Detected> detections = new ArrayList<>();
		for (int i = 0; i < found.size(); i++) {
			FoundFiducial f = found.get(i);

			Detected detected = new Detected();
			detected.binary = foundBinary.get(i);
			detected.location = f.distortedPixels;

			detections.add( detected );
		}

		return detections;
	}

	@Override
	protected boolean processSquare(GrayF32 gray, Result result , double edgeInside, double edgeOutside) {
		GrayU8 binary = foundBinary.grow();
		int off = (gray.width-binary.width)/2;
		gray.subimage(off, off, gray.width - off, gray.width - off, grayNoBorder);

		threshold.process(grayNoBorder, binary, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO);
		return true;
	}

	public static class Detected {
		public GrayU8 binary;
		public Quadrilateral_F64 location;
	}
}
