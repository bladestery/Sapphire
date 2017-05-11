/*
 * Copyright (c) 2011-2016, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.factory.feature.detect.interest;

import boofcv.abst.feature.describe.ConfigSiftScaleSpace;
import boofcv.abst.feature.detect.extract.NonMaxLimiter;
import boofcv.abst.feature.detect.extract.NonMaxSuppression;
import boofcv.abst.feature.detect.interest.*;
import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.abst.filter.derivative.ImageHessian;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.*;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.orientation.FactoryOrientation;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.image.ImageGray;
import boofcv.struct.pyramid.PyramidFloat;
import sapphire.app.SapphireObject;

/**
 * <p>Factory for creating interest point detectors which conform to the {@link InterestPointDetector}
 * interface </p>
 * <p>
 * NOTE: Higher level interface than {@link GeneralFeatureDetector}.  This will automatically
 * compute image derivatives across scale space as needed, unlike GeneralFeatureDetector which
 * just detects features at a particular scale and requires image derivatives be passed in.
 * </p>
 *
 * @author Peter Abeles
 * @see FactoryFeatureExtractor
 */
public class FactoryInterestPoint implements SapphireObject {
	public FactoryInterestPoint() {}
	/**
	 * Wraps {@link GeneralFeatureDetector} inside an {@link InterestPointDetector}.
	 *
	 * @param feature   Feature detector.
	 * @param scale Scale of detected features
	 * @param inputType Image type of input image.
	 * @param derivType Image type for gradient.
	 * @return The interest point detector.
	 */
	public <T extends ImageGray, D extends ImageGray>
	InterestPointDetector<T> wrapPoint(GeneralFeatureDetector<T, D> feature, double scale , Class<T> inputType, Class<D> derivType,
									   FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB) {

		ImageGradient<T, D> gradient = null;
		ImageHessian<D> hessian = null;

		if (feature.getRequiresGradient() || feature.getRequiresHessian())
			gradient = FD.sobel(inputType, derivType, GIO, FIB);
		if (feature.getRequiresHessian())
			hessian = FD.hessianSobel(derivType, GIO, FIB);

		return new GeneralToInterestPoint<>(feature, gradient, hessian, scale, derivType, GIO);
	}

	/**
	 * Wraps {@link FeatureLaplacePyramid} inside an {@link InterestPointDetector}.
	 *
	 * @param feature   Feature detector.
	 * @param scales    Scales at which features are detected at.
	 * @param pyramid   Should it be constructed as a pyramid or scale-space
	 * @param inputType Image type of input image.
	 * @return The interest point detector.
	 */
	public <T extends ImageGray, D extends ImageGray>
	InterestPointDetector<T> wrapDetector(FeatureLaplacePyramid<T, D> feature,
										  double[] scales, boolean pyramid,
										  Class<T> inputType) {

		PyramidFloat<T> ss;

		if( pyramid )
			ss = FactoryPyramid.scaleSpacePyramid(scales, inputType);
		else
			ss = FactoryPyramid.scaleSpace(scales, inputType);

		return new WrapFLPtoInterestPoint<>(feature, ss);
	}

	/**
	 * Wraps {@link FeaturePyramid} inside an {@link InterestPointDetector}.
	 *
	 * @param feature   Feature detector.
	 * @param scales    Scales at which features are detected at.
	 * @param pyramid   Should it be constructed as a pyramid or scale-space
	 * @param inputType Image type of input image.
	 * @return The interest point detector.
	 */
	public <T extends ImageGray, D extends ImageGray>
	InterestPointDetector<T> wrapDetector(FeaturePyramid<T, D> feature,
										  double[] scales, boolean pyramid,
										  Class<T> inputType) {

		PyramidFloat<T> ss;

		if( pyramid )
			ss = FactoryPyramid.scaleSpacePyramid(scales, inputType);
		else
			ss = FactoryPyramid.scaleSpace(scales, inputType);

		return new WrapFPtoInterestPoint<>(feature, ss);
	}

	/**
	 * Creates a {@link FastHessianFeatureDetector} detector which is wrapped inside
	 * an {@link InterestPointDetector}
	 *
	 * @param config Configuration for detector.  Pass in null for default options.
	 * @return The interest point detector.
	 * @see FastHessianFeatureDetector
	 */
	public <T extends ImageGray>
	InterestPointDetector<T> fastHessian( ConfigFastHessian config, FactoryInterestPointAlgs FIPA, FactoryFeatureExtractor FFE) {
		return new WrapFHtoInterestPoint(FIPA.fastHessian(config, FFE));
	}

	public <T extends ImageGray>
	InterestPointDetector<T> sift(ConfigSiftScaleSpace configSS ,
								  ConfigSiftDetector configDet , Class<T> imageType, FactoryFeatureExtractor FFE, FactoryImageBorder FIB, FactoryKernelGaussian FKG,
								  GeneralizedImageOps GIO) {

		if( configSS == null )
			configSS = new ConfigSiftScaleSpace();
		if( configDet == null )
			configDet = new ConfigSiftDetector();

		SiftScaleSpace scaleSpace =
				new SiftScaleSpace(configSS.firstOctave,configSS.lastOctave,configSS.numScales,configSS.sigma0, FKG);
		NonMaxSuppression nonmax = FFE.nonmax(configDet.extract);
		NonMaxLimiter limiter = new NonMaxLimiter(nonmax,configDet.maxFeaturesPerScale);
		SiftDetector detector = new SiftDetector(scaleSpace,configDet.edgeR,limiter, FIB, GIO);

		return new WrapSiftDetector<>(detector, imageType);
	}
}
