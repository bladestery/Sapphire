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

package boofcv.abst.feature.orientation;

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * Converts an implementation of {@link OrientationGradient} into {@link OrientationImage}.
 *
 * @author Peter Abeles
 */
public class OrientationGradientToImage<T extends ImageGray, D extends ImageGray>
	implements OrientationImage<T>
{
	ImageGradient<T,D> gradient;
	OrientationGradient<D> alg;

	// storage for image gradient
	D derivX;
	D derivY;

	// Input image type
	Class<T> inputType;

	public OrientationGradientToImage(OrientationGradient<D> alg,
									  ImageGradient<T, D> gradient,
									  Class<T> inputType ,
									  Class<D> gradientType, GeneralizedImageOps GIO) {
		this.alg = alg;
		this.gradient = gradient;
		this.inputType = inputType;

		derivX = GIO.createSingleBand(gradientType,1,1);
		derivY = GIO.createSingleBand(gradientType,1,1);
	}

	@Override
	public void setImage(T image, InputSanityCheck ISC, GeneralizedImageOps GIO, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
						 ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryKernelGaussian FKG,
						 GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI,
						 FactoryImageBorder FIB, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB,
						 ConvolveNormalized CN, ImageType IT, FactoryInterpolation FI) {
		derivX.reshape(image.width,image.height);
		derivY.reshape(image.width,image.height);

		gradient.process(image,derivX,derivY, ISC,DHF, CINB, CJBG,GSO,GSUO);
		alg.setImage(derivX,derivY, ISC);
	}

	@Override
	public Class<T> getImageType() {
		return inputType;
	}

	@Override
	public void setObjectRadius(double radius, FactoryKernelGaussian FKG) {
		alg.setObjectRadius(radius, FKG);
	}

	@Override
	public double compute(double c_x, double c_y, FastHessianFeatureDetector FHFD) {
		return alg.compute(c_x,c_y, FHFD);
	}
}
