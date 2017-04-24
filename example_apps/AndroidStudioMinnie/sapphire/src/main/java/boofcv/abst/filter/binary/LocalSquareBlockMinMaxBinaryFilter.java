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
import boofcv.alg.filter.binary.ThresholdSquareBlockMinMax;
import boofcv.alg.filter.binary.impl.ThresholdSquareBlockMinMax_F32;
import boofcv.alg.filter.binary.impl.ThresholdSquareBlockMinMax_U8;
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
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * Wrapper around {@link ThresholdSquareBlockMinMax}.
 *
 * @author Peter Abeles
 */
public class LocalSquareBlockMinMaxBinaryFilter<T extends ImageGray>
		implements InputToBinary<T>
{
	ThresholdSquareBlockMinMax alg;
	ImageType<T> imageType;

	public LocalSquareBlockMinMaxBinaryFilter(double minimumSpread, int requestedBlockWidth, double scale , boolean down, Class<T> imageType, ImageType IT) {

		if( imageType == GrayF32.class )
			this.alg = new ThresholdSquareBlockMinMax_F32((float)minimumSpread,requestedBlockWidth,(float)scale,down);
		else if( imageType == GrayU8.class )
			this.alg = new ThresholdSquareBlockMinMax_U8(minimumSpread,requestedBlockWidth,scale,down);
		else
			throw new IllegalArgumentException("Unsupported image type");

		this.imageType = IT.single(imageType);
	}

	@Override
	public void process(T input, GrayU8 output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO) {
		alg.process(input,output);
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
		return imageType;
	}

	@Override
	public ImageType<GrayU8> getOutputType(ImageType IT) {
		return IT.single(GrayU8.class);
	}
}
