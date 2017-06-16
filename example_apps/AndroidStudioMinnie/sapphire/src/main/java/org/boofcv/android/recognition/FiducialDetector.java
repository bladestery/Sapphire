package org.boofcv.android.recognition;

import org.ddogleg.struct.FastQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import boofcv.abst.filter.binary.InputToBinary;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.fiducial.square.BaseDetectFiducialSquare;
import boofcv.alg.fiducial.square.FoundFiducial;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.LinearContourLabelChang2004;
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
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.shape.ConfigPolygonDetector;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageType;
import georegression.struct.shapes.Quadrilateral_F64;
import sapphire.app.SapphireObject;

/**
 * Detects fiducials inside of an image.  Used to return the pattern inside.
 *
 * @author Peter Abeles
 */
public class FiducialDetector extends BaseDetectFiducialSquare<GrayU8> {
	private static final String TAG = "FiducialDetector";

	// Width of black border (units = pixels)
	private final static int w=32;
	private final static int squareLength=w*4; // this must be a multiple of 16

	private InputToBinary<GrayF32> threshold;
	private GrayF32 grayNoBorder = new GrayF32();

	// All the images inside which it found
	private FastQueue<GrayU8> foundBinary;

	public FiducialDetector(FactoryThresholdBinary FTB, ImageType IT, FactoryShapeDetector FSD, FactoryDistort FDs, FactoryInterpolation FI, FactoryImageBorder FIB) {
		super(FTB.globalOtsu(0, 255, true, GrayU8.class, IT), FSD.polygon(
						new ConfigPolygonDetector(false, 4, 4), GrayU8.class),
				0.25, 0.5, squareLength + squareLength, GrayU8.class, FI, FDs, FIB);

		threshold = FTB.globalOtsu(0,255,true,GrayF32.class, IT);

		foundBinary = new FastQueue<GrayU8>(GrayU8.class,true) {
			@Override
			protected GrayU8 createInstance() {
				return new GrayU8(squareLength,squareLength);
			}
		};
	}

	@Override
	public void process(GrayU8 gray, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
						ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
						ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						ConvertImage CI, UtilWavelet UW, ImageType IT, LinearContourLabelChang2004 cF) {
		foundBinary.reset();
		super.process(gray, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT, cF);
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
	protected boolean processSquare(GrayF32 gray, Result result , double edgeInside, double edgeOutside, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
									ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
									ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
									ConvertImage CI, UtilWavelet UW, ImageType IT) {
		GrayU8 binary = foundBinary.grow();
		int off = (gray.width-binary.width)/2;
		gray.subimage(off, off, gray.width - off, gray.width - off, grayNoBorder);

		threshold.process(grayNoBorder, binary, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
		return true;
	}

	public static class Detected implements Serializable {
		public GrayU8 binary;
		public Quadrilateral_F64 location;
	}
}
