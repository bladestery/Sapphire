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

import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.feature.detect.interest.SiftScaleSpace;
import boofcv.alg.feature.detect.interest.UnrollSiftScaleSpaceGradient;
import boofcv.alg.feature.orientation.OrientationHistogramSift;
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
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.BoofDefaults;
import boofcv.struct.image.FactoryImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import sapphire.compiler.FDGenerator;

/**
 * Wrapper around {@link OrientationHistogramSift} for {@link OrientationImage}.  Selects
 * the best solution from the multiple solutions.
 *
 * @author Peter Abeles
 */
public class OrientationSiftToImage<T extends ImageGray>
		implements OrientationImage<T>
{
	UnrollSiftScaleSpaceGradient scaleSpace;
	OrientationHistogramSift<GrayF32> alg;
	UnrollSiftScaleSpaceGradient.ImageScale image;
	double sigma = 1.0/BoofDefaults.SIFT_SCALE_TO_RADIUS;

	Class<T> imageType;
	GrayF32 imageFloat = new GrayF32(1,1);

	public OrientationSiftToImage(OrientationHistogramSift<GrayF32> alg,
								  SiftScaleSpace ss, Class<T> imageType, FactoryDerivative FD,
								  GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		this.alg = alg;
		this.scaleSpace = new UnrollSiftScaleSpaceGradient(ss, FD, GIO, FIB);
		this.imageType = imageType;
	}

	@Override
	public void setImage(T image, InputSanityCheck ISC, GeneralizedImageOps GIO, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
						 ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryKernelGaussian FKG,
						 GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI, FactoryImageBorder FIB, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB,
						 ConvolveNormalized CN, ImageType IT, FactoryInterpolation FI) {

		GrayF32 input;
		if( image instanceof GrayF32) {
			input = (GrayF32)image;
		} else {
			imageFloat.reshape(image.width,image.height);
			GConvertImage.convert(image,imageFloat, ISC, GIO, GIMO, IMO, CI, IT);
			input = imageFloat;
		}

		scaleSpace.setImage(input, FIB, ISC, CNN, CINB, CNJB, CN, DHF, CJBG, GSO, GSUO, FI);
		setObjectRadius(sigma*BoofDefaults.SIFT_SCALE_TO_RADIUS, FKG);
	}

	@Override
	public Class<T> getImageType() {
		return imageType;
	}

	@Override
	public void setObjectRadius(double radius, FactoryKernelGaussian FKG) {
		sigma = radius / BoofDefaults.SIFT_SCALE_TO_RADIUS;
		this.image = scaleSpace.lookup(sigma);
	}

	@Override
	public double compute(double c_x, double c_y, FastHessianFeatureDetector FHFD) {
		alg.setImageGradient(image.derivX,image.derivY);

		double imageToInput = image.imageToInput;
		alg.process(c_x/imageToInput,c_y/imageToInput, sigma/imageToInput, FHFD);

		return alg.getPeakOrientation();
	}
}
