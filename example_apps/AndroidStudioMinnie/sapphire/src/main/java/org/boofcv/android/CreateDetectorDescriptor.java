package org.boofcv.android;

import boofcv.abst.feature.describe.ConfigBrief;
import boofcv.abst.feature.describe.DescribeRegionPoint;
import boofcv.abst.feature.detdesc.ConfigCompleteSift;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.interest.ConfigFast;
import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.detect.interest.ConfigSiftDetector;
import boofcv.abst.feature.detect.interest.InterestPointDetector;
import boofcv.abst.feature.orientation.OrientationImage;
import boofcv.abst.feature.orientation.OrientationIntegral;
import boofcv.alg.feature.detect.interest.GeneralFeatureDetector;
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.alg.transform.ii.GIntegralImageOps;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.feature.describe.FactoryDescribeRegionPoint;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.factory.feature.detect.edge.FactoryEdgeDetectors;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPoint;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.feature.detect.interest.FactoryDetectPoint;
import boofcv.factory.feature.detect.interest.FactoryInterestPoint;
import boofcv.factory.feature.detect.interest.FactoryInterestPointAlgs;
import boofcv.factory.feature.orientation.FactoryOrientation;
import boofcv.factory.feature.orientation.FactoryOrientationAlgs;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageType;
import sapphire.app.SapphireObject;
import sapphire.compiler.FDGenerator;

/**
 * Class which is intended to make it easier to create instances of DetectDescribePoint.  It will automatically
 * check to see if there is a specialized version of some algorithm.  If not it will declare its components individually
 * then combine them.
 *
 * @author Peter Abeles
 */
public class CreateDetectorDescriptor implements SapphireObject {
	public static final int DETECT_FH = 0;
	public static final int DETECT_SIFT = 1;
	public static final int DETECT_SHITOMASI = 2;
	public static final int DETECT_HARRIS = 3;
	public static final int DETECT_FAST = 4;

	public static final int DESC_SURF = 0;
	public static final int DESC_SIFT = 1;
	public static final int DESC_BRIEF = 2;
	public static final int DESC_NCC = 3;

	public CreateDetectorDescriptor() {}

	public DetectDescribePoint create(int detect , int describe , Class imageType, FactoryInterestPoint FIP, FactoryInterestPointAlgs FIrPA, FactoryIntensityPointAlg FIPA, FactoryFeatureExtractor FFE, FactoryImageBorder FIB, FactoryDerivative FD,
									  GeneralizedImageOps GIO, FactoryKernelGaussian FKG, ImageType IT, FactoryBlurFilter FBF) {

		if( detect == DETECT_FH && describe == DESC_SURF ) {
			return FactoryDetectDescribe.surfFast(confDetectFH(), null, null, imageType, FFE, FIrPA, FKG);
		} else if( detect == DETECT_SIFT && describe == DESC_SIFT ) {
			return FactoryDetectDescribe.sift( confSift() , FFE, FKG, FIB, GIO, FD);
		} else {
			boolean ss = isScaleSpace(detect);

			InterestPointDetector detector = createDetector(detect,imageType, FIP, FIrPA, FIPA, FFE, FIB, FD, GIO, FKG);
			DescribeRegionPoint descriptor = createDescriptor(describe,ss,imageType, FKG, IT, FBF, GIO, FIB, FD);
			OrientationImage ori = createOrientation(detect,imageType, FD, GIO, FIB, FKG);

			return FactoryDetectDescribe.fuseTogether(detector,ori,descriptor);
		}
	}

	private OrientationImage createOrientation(int detect, Class imageType, FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB, FactoryKernelGaussian FKG) {
		// OK this is a bit lazy...
		if( isScaleSpace(detect)) {
			Class integralType = GIntegralImageOps.getIntegralType(imageType);
			OrientationIntegral orientationII = FactoryOrientationAlgs.sliding_ii(null, integralType, FKG);
			return FactoryOrientation.convertImage(orientationII, imageType, FD, GIO, FIB);
		} else {
			return null;
		}
	}

	public boolean isScaleSpace( int detector ) {
		return detector == DETECT_FH || detector == DETECT_SIFT;
	}

	public InterestPointDetector createDetector(int detect , Class imageType, FactoryInterestPoint FIP, FactoryInterestPointAlgs FIrPA, FactoryIntensityPointAlg FIPA, FactoryFeatureExtractor FFE, FactoryImageBorder FIB, FactoryDerivative FD,
												GeneralizedImageOps GIO, FactoryKernelGaussian FKG) {
		Class derivType = GImageDerivativeOps.getDerivativeType(imageType);
		GeneralFeatureDetector general;

		switch( detect ) {
			case DETECT_FH:
				return FIP.fastHessian(confDetectFH(), FIrPA, FFE);

			case DETECT_SIFT:
				return FIP.sift(null,confDetectSift(),imageType, FFE, FIB, FKG, GIO);

			case DETECT_SHITOMASI:
				general = FactoryDetectPoint.createShiTomasi(confCorner(),false,derivType, FIPA, GIO, FKG, FFE);
				break;

			case DETECT_HARRIS:
				general = FactoryDetectPoint.createHarris(confCorner(), false, derivType, FIPA, GIO, FKG, FFE);
				break;

			case DETECT_FAST:
				general = FactoryDetectPoint.createFast(new ConfigFast(20,9),new ConfigGeneralDetector(150,3,20), imageType, FIPA, FFE);
				break;

			default:
				throw new RuntimeException("Unknown detector");

		}

		return FIP.wrapPoint(general,1.0,imageType,derivType, FD, GIO, FIB);
	}

	public DescribeRegionPoint createDescriptor(int describe , boolean scaleSpace , Class imageType, FactoryKernelGaussian FKG, ImageType IT, FactoryBlurFilter FBF, GeneralizedImageOps GIO,
												FactoryImageBorder FIB, FactoryDerivative FD) {
		switch( describe ) {
			case DESC_SURF:
				return FactoryDescribeRegionPoint.surfFast(null, imageType, FKG, IT);

			case DESC_SIFT:
				return FactoryDescribeRegionPoint.sift(null,null,imageType, FKG, IT, FD, GIO, FIB);

			case DESC_BRIEF:
				return FactoryDescribeRegionPoint.brief(new ConfigBrief(!scaleSpace),imageType, FBF, GIO, IT, FIB);

			case DESC_NCC:
				return FactoryDescribeRegionPoint.pixelNCC(9,9,imageType, IT);

			default:
				throw new RuntimeException("Unknown descriptor");
		}


	}

	private static ConfigGeneralDetector confCorner() {
		ConfigGeneralDetector conf = new ConfigGeneralDetector();
		conf.radius = 3;
		conf.threshold = 20;
		conf.maxFeatures = 150;
		return conf;
	}

	private static  ConfigFastHessian confDetectFH() {
		ConfigFastHessian conf = new ConfigFastHessian();
		conf.initialSampleSize = 2;
		conf.extractRadius = 2;
		conf.maxFeaturesPerScale = 120;
		return conf;
	}

	private static ConfigCompleteSift confSift() {
		ConfigCompleteSift config = new ConfigCompleteSift();
		config.detector = confDetectSift();
		return config;
	}

	private static  ConfigSiftDetector confDetectSift() {
		ConfigSiftDetector conf = new ConfigSiftDetector();
		conf.extract.radius = 3;
		conf.extract.threshold = 2;
		conf.maxFeaturesPerScale = 120;
		return conf;
	}

}
