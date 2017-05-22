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

package boofcv.abst.feature.dense;

import boofcv.abst.feature.describe.DescriptorInfo;
import boofcv.alg.InputSanityCheck;
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
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_I32;

import java.util.List;

// TODO provide way for selecting sampling method  REGULAR_GRID, MAX_SPREAD
// TODO easy way to know coordinate for grid image

/**
 * Computes feature descriptors across the whole image.  No feature detection is performed.  Descriptions are typically
 * computed at regular intervals.  Scale, orientation, and other local geometric information is typically not computed
 * or returned to the user.
 *
 * @author Peter Abeles
 */
public interface DescribeImageDense<T extends ImageBase, Desc extends TupleDesc>
	extends DescriptorInfo<Desc>
{
	/**
	 * Processes the image and computes the dense image features.
	 *
	 * @param input Input image.
	 */
	void process(T input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
				 ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
				 ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
				 ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB);

	/**
	 * <p>Returns a list of the computed descriptions.</p>
	 *
	 * <p>The list and everything contained inside of it are owned by this class and subject to modification
	 * the next time {@link #process(ImageBase)} is called.</p>
	 *
	 * @return list of descriptions
	 */
	List<Desc> getDescriptions();

	/**
	 * <p>Returns a list of locations that the descriptors are computed at</p>
	 *
	 * <p>The list and everything contained inside of it are owned by this class and subject to modification
	 * the next time {@link #process(ImageBase)} is called.</p>
	 *
	 * @return list of descriptions
	 */
	List<Point2D_I32> getLocations();

	/**
	 * Description of the type of image it can process
	 *
	 * @return ImageDataType
	 */
	ImageType<T> getImageType();
}
