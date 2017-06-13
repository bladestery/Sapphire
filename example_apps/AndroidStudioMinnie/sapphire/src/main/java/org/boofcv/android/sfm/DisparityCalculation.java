package org.boofcv.android.sfm;

import android.util.Log;

import org.ddogleg.fitting.modelset.DistanceFromModel;
import org.ddogleg.fitting.modelset.ModelGenerator;
import org.ddogleg.fitting.modelset.ModelManager;
import org.ddogleg.fitting.modelset.ModelMatcher;
import org.ddogleg.fitting.modelset.ransac.Ransac;
import org.ddogleg.struct.FastQueue;
import org.ejml.data.DenseMatrix64F;

import java.util.ArrayList;
import java.util.List;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.disparity.StereoDisparity;
import boofcv.abst.geo.Estimate1ofEpipolar;
import boofcv.abst.geo.TriangulateTwoViewsCalibrated;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.distort.ImageDistort;
import boofcv.alg.distort.LensDistortionOps;
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
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.LaplacianEdge;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.geo.PerspectiveOps;
import boofcv.alg.geo.RectifyImageOps;
import boofcv.alg.geo.rectify.RectifyCalibrated;
import boofcv.alg.geo.robust.DistanceSe3SymmetricSq;
import boofcv.alg.geo.robust.Se3FromEssentialGenerator;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.geo.EnumEpipolar;
import boofcv.factory.geo.FactoryMultiView;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.calib.CameraPinholeRadial;
import boofcv.struct.distort.Point2Transform2_F64;
import boofcv.struct.feature.AssociatedIndex;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.geo.AssociatedPair;
import boofcv.struct.image.FactoryImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import georegression.fitting.se.ModelManagerSe3_F64;
import georegression.struct.point.Point2D_F64;
import georegression.struct.se.Se3_F64;

/**
 * Computes the disparity image from two views.  Features are first associated between the two images, image motion
 * found, rectification and dense stereo calculation.
 *
 * @author Peter Abeles
 */
public class DisparityCalculation<Desc extends TupleDesc> {
	DetectDescribePoint<GrayF32,Desc> detDesc;
	AssociateDescription<Desc> associate;
	CameraPinholeRadial intrinsic;

	StereoDisparity<GrayF32, GrayF32> disparityAlg;

	FastQueue<Desc> listSrc;
	FastQueue<Desc> listDst;
	FastQueue<Point2D_F64> locationSrc = new FastQueue<Point2D_F64>(Point2D_F64.class,true);
	FastQueue<Point2D_F64> locationDst = new FastQueue<Point2D_F64>(Point2D_F64.class,true);

	List<AssociatedPair> inliersPixel;

	boolean directionLeftToRight;

	public GrayF32 distortedLeft;
	public GrayF32 distortedRight;
	public GrayF32 rectifiedLeft;
	public GrayF32 rectifiedRight;

	// Laplacian that has been applied to rectified images
	GrayF32 edgeLeft;
	GrayF32 edgeRight;

	// has the disparity been computed
	boolean computedDisparity = false;

	public DisparityCalculation(DetectDescribePoint<GrayF32, Desc> detDesc,
								AssociateDescription<Desc> associate ,
								CameraPinholeRadial intrinsic ) {
		this.detDesc = detDesc;
		this.associate = associate;
		this.intrinsic = intrinsic;

		listSrc = UtilFeature.createQueue(detDesc, 10);
		listDst = UtilFeature.createQueue(detDesc, 10);
	}

	public void setDisparityAlg(StereoDisparity<GrayF32, GrayF32> disparityAlg) {
		this.disparityAlg = disparityAlg;
	}

	public void init( int width , int height ) {
		distortedLeft = new GrayF32(width,height);
		distortedRight = new GrayF32(width,height);
		rectifiedLeft = new GrayF32(width,height);
		rectifiedRight = new GrayF32(width,height);
		edgeLeft = new GrayF32(width,height);
		edgeRight = new GrayF32(width,height);
	}

	public void setSource(GrayF32 image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO,
						  GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
						  GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN,
						  ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						  FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		distortedLeft.setTo(image);
		detDesc.detect(image, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
		describeImage(listSrc, locationSrc);
		associate.setSource(listSrc);
	}

	public void setDestination( GrayF32 image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO,
								GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
								GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN,
								ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
								FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs ) {
		distortedRight.setTo(image);
		detDesc.detect(image, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
		describeImage(listDst, locationDst);
		associate.setDestination(listDst);

	}

	private void describeImage(FastQueue<Desc> listDesc, FastQueue<Point2D_F64> listLoc) {
		listDesc.reset();
		listLoc.reset();
		int N = detDesc.getNumberOfFeatures();
		for( int i = 0; i < N; i++ ) {
			listLoc.grow().set(detDesc.getLocation(i));
			listDesc.grow().setTo(detDesc.getDescription(i));
		}
	}

	/**
	 * Associates image features, computes camera motion, and rectifies images.
	 *
	 * @return true it was able to rectify the input images or false if not
	 */
	public boolean rectifyImage(ImageMiscOps IMO, InputSanityCheck ISC, ImageType IT, FactoryImageBorder FIB, FactoryInterpolation FI, FactoryDistort FDs, LensDistortionOps LDO) {
		computedDisparity = false;

		associate.associate();
		List<AssociatedPair> pairs = convertToNormalizedCoordinates(LDO);

		Se3_F64 leftToRight = estimateCameraMotion(pairs);

		if( leftToRight == null ) {
			Log.e("disparity","estimate motion failed");
			Log.e("disparity","  left.size = "+locationSrc.size());
			Log.e("disparity","  right.size = "+locationDst.size());
			Log.e("disparity", "  associated size = " + associate.getMatches().size());
			Log.e("disparity","  pairs.size = "+pairs.size());

			return false;
		} else if( leftToRight.getT().x > 0 ) {
			// the user took a picture from right to left instead of left to right
			// so now everything needs to be swapped
			leftToRight = leftToRight.invert(null);
			GrayF32 tmp = distortedLeft;
			distortedLeft = distortedRight;
			distortedRight = tmp;
			tmp = edgeLeft;
			edgeLeft = edgeRight;
			edgeRight = tmp;
			directionLeftToRight = false;
		} else {
			directionLeftToRight = true;
		}

		DenseMatrix64F rectifiedK = new DenseMatrix64F(3,3);
		rectifyImages(leftToRight, rectifiedK, IMO, ISC, IT, FIB, FI, FDs, LDO);

		return true;
	}

	/**
	 * Computes the disparity between the two rectified images
	 */
	public void computeDisparity(GImageMiscOps GIMO, ImageMiscOps IMO, GeneralizedImageOps GIO, InputSanityCheck ISC) {
		if( disparityAlg == null )
			return;

		disparityAlg.process(edgeLeft, edgeRight, GIMO, IMO, GIO, ISC);
		computedDisparity = true;
	}

	/**
	 * Convert a set of associated point features from pixel coordinates into normalized image coordinates.
	 */
	public List<AssociatedPair> convertToNormalizedCoordinates(LensDistortionOps LDO) {

		Point2Transform2_F64 tran = LDO.transformPoint(intrinsic).undistort_F64(true,false);

		List<AssociatedPair> calibratedFeatures = new ArrayList<AssociatedPair>();

		FastQueue<AssociatedIndex> matches = associate.getMatches();
		for( AssociatedIndex a : matches.toList() ) {
			Point2D_F64 p1 = locationSrc.get( a.src );
			Point2D_F64 p2 = locationDst.get( a.dst );

			AssociatedPair c = new AssociatedPair();

			tran.compute(p1.x, p1.y, c.p1);
			tran.compute(p2.x, p2.y, c.p2);

			calibratedFeatures.add(c);
		}

		return calibratedFeatures;
	}

	/**
	 * Estimates image motion up to a scale factor
	 * @param matchedNorm List of associated features in normalized image coordinates
	 */
	public volatile int numInside = 0;
	public Se3_F64 estimateCameraMotion( List<AssociatedPair> matchedNorm )
	{
		numInside++;
		System.out.println("DISPARITY "+numInside);
		Estimate1ofEpipolar essentialAlg = FactoryMultiView.computeFundamental_1(EnumEpipolar.ESSENTIAL_5_NISTER, 5);
		TriangulateTwoViewsCalibrated triangulate = FactoryMultiView.triangulateTwoGeometric();
		ModelGenerator<Se3_F64, AssociatedPair> generateEpipolarMotion =
				new Se3FromEssentialGenerator(essentialAlg, triangulate);

		DistanceFromModel<Se3_F64, AssociatedPair> distanceSe3 =
				new DistanceSe3SymmetricSq(triangulate,
						intrinsic.fx, intrinsic.fy, intrinsic.skew,
						intrinsic.fx, intrinsic.fy, intrinsic.skew);

		// 1/2 a pixel tolerance for RANSAC inliers
		double ransacTOL = 0.5 * 0.5 * 2.0;

		ModelManager<Se3_F64> mm = new ModelManagerSe3_F64();

		ModelMatcher<Se3_F64, AssociatedPair> epipolarMotion =
				new Ransac<Se3_F64, AssociatedPair>(2323, mm,generateEpipolarMotion, distanceSe3,
						300, ransacTOL);

		if (!epipolarMotion.process(matchedNorm)) {
			numInside--;
			return null;
		}

		createInliersList(epipolarMotion);

		numInside--;
		return epipolarMotion.getModelParameters();
	}

	/**
	 * Save a list of inliers in pixel coordinates
	 */
	private void createInliersList( ModelMatcher<Se3_F64, AssociatedPair> epipolarMotion ) {
		inliersPixel = new ArrayList<AssociatedPair>();

		FastQueue<AssociatedIndex> matches = associate.getMatches();

		int N = epipolarMotion.getMatchSet().size();
		for( int i = 0; i < N; i++ ) {

			AssociatedIndex a = matches.get( epipolarMotion.getInputIndex(i));

			Point2D_F64 p1 = locationSrc.get( a.src );
			Point2D_F64 p2 = locationDst.get( a.dst );

			inliersPixel.add( new AssociatedPair(p1,p2));
		}
	}

	/**
	 * Remove lens distortion and rectify stereo images
	 *
	 * @param leftToRight    Camera motion from left to right
	 * @param rectifiedK     Output camera calibration matrix for rectified camera
	 */
	public void rectifyImages(Se3_F64 leftToRight,
							  DenseMatrix64F rectifiedK, ImageMiscOps IMO, InputSanityCheck ISC, ImageType IT, FactoryImageBorder FIB,
							  FactoryInterpolation FI, FactoryDistort FDs, LensDistortionOps LDO) {
		RectifyCalibrated rectifyAlg = RectifyImageOps.createCalibrated();

		// original camera calibration matrices
		DenseMatrix64F K = PerspectiveOps.calibrationMatrix(intrinsic, null);

		rectifyAlg.process(K, new Se3_F64(), K, leftToRight);

		// rectification matrix for each image
		DenseMatrix64F rect1 = rectifyAlg.getRect1();
		DenseMatrix64F rect2 = rectifyAlg.getRect2();

		// New calibration matrix,
		rectifiedK.set(rectifyAlg.getCalibrationMatrix());

		// Adjust the rectification to make the view area more useful
//		RectifyImageOps.allInsideLeft(intrinsic, rect1, rect2, rectifiedK);

		// undistorted and rectify images
		ImageDistort<GrayF32,GrayF32> distortLeft =
				RectifyImageOps.rectifyImage(intrinsic, rect1, BorderType.ZERO, IT.single(GrayF32.class), FIB, FI, FDs, LDO);
		ImageDistort<GrayF32,GrayF32> distortRight =
				RectifyImageOps.rectifyImage(intrinsic, rect2, BorderType.ZERO, IT.single(GrayF32.class), FIB, FI, FDs, LDO);

		// Apply the Laplacian for some lighting invariance
		IMO.fill(rectifiedLeft,0);
		distortLeft.apply(distortedLeft, rectifiedLeft);
		LaplacianEdge.process(rectifiedLeft,edgeLeft, ISC);

		IMO.fill(rectifiedRight, 0);
		distortRight.apply(distortedRight, rectifiedRight);
		LaplacianEdge.process(rectifiedRight,edgeRight, ISC);
	}

	public List<AssociatedPair> getInliersPixel() {
		return inliersPixel;
	}

	public GrayF32 getDisparity() {
		return disparityAlg.getDisparity();
	}

	public StereoDisparity<GrayF32, GrayF32> getDisparityAlg() {
		return disparityAlg;
	}

	public boolean isDisparityAvailable() {
		return computedDisparity;
	}

	public boolean isDirectionLeftToRight() {
		return directionLeftToRight;
	}
}
