/*
 * Copyright (c) 2011-2014, Peter Abeles. All Rights Reserved.
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

package boofcv.abst.filter;

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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;


/**
 * Generalized interface for processing images.
 *
 * @author Peter Abeles
 */
public interface FilterImageInterface<Input extends ImageBase, Output extends ImageBase>
{
	/**
	 * Processes the input image and writes the results to the output image.
	 *
	 * @param input Input image.
	 * @param output Output image.
	 */
	public void process(Input input , Output output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						ConvertImage CI, UtilWavelet UW);

	/**
	 * How many pixels are not processed along the horizontal border.
	 *
	 * @return Border size in pixels.
	 */
	public int getHorizontalBorder();

	/**
	 * How many pixels are not processed along the vertical border.
	 *
	 * @return Border size in pixels.
	 */
	public int getVerticalBorder();

	/**
	 * Specifies the input image type
	 *
	 * @return Input image type.
	 */
	public ImageType<Input> getInputType(ImageType IT);

	/**
	 * Specifies the output image type
	 *
	 * @return Output image type.
	 */
	public ImageType<Output> getOutputType(ImageType IT);
}
