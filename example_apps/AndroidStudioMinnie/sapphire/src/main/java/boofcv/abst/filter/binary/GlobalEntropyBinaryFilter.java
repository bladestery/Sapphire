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

package boofcv.abst.filter.binary;

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
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * Computes a threshold based on entropy to create a binary image
 *
 * @see boofcv.alg.filter.binary.GThresholdImageOps#computeEntropy(ImageGray, int, int)
 *
 * @author Peter Abeles
 */
public class GlobalEntropyBinaryFilter<T extends ImageGray> implements InputToBinary<T> {
	ImageType<T> inputType;
	private static GThresholdImageOps GTIO;
	private static ThresholdImageOps TIO;
	private static InputSanityCheck ISC;
	private static GeneralizedImageOps GIO;
	private static ImageStatistics IS;
	boolean down;
	int minValue;
	int maxValue;

	/**
	 * @see GThresholdImageOps#computeEntropy
	 */
	public GlobalEntropyBinaryFilter(int minValue, int maxValue,
									 boolean down, ImageType<T> inputType) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.down = down;
		this.inputType = inputType;
	}

	@Override
	public void process(T input, GrayU8 output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO) {
		double threshold = GTIO.computeEntropy(input, minValue, maxValue, IS);
		GTIO.threshold(input,output,threshold,down, TIO, ISC, GIO);
	}

	@Override
	public int getHorizontalBorder() {
		return 0;
	}

	@Override
	public int getVerticalBorder() {
		return 0;
	}

	@Override
	public ImageType<T> getInputType(ImageType IT) {
		return inputType;
	}

	@Override
	public ImageType<GrayU8> getOutputType(ImageType IT) {
		return IT.single(GrayU8.class);
	}
}
