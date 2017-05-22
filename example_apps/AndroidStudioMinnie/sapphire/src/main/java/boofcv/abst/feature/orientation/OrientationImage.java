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
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageGray;


/**
 * Estimates the orientation of a region directly from the image's pixels.
 *
 * @author Peter Abeles
 */
public interface OrientationImage <T extends ImageGray> extends RegionOrientation {
	/**
	 * Specifies input image data for estimating orientation.
	 *
	 * @param image Input image..
	 */
	public void setImage(T image, InputSanityCheck ISC, GeneralizedImageOps GIO, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
						 ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryKernelGaussian FKG, GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI,
			FactoryImageBorder FIB, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB,
						 ConvolveNormalized CN);

	/**
	 * Returns the type of image it can process.
	 *
	 * @return Type of image which can be processed
	 */
	public Class<T> getImageType();
}
