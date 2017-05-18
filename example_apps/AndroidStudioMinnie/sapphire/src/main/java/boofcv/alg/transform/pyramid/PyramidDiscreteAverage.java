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

package boofcv.alg.transform.pyramid;

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
import boofcv.alg.filter.misc.AverageDownSampleOps;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageGray;
import boofcv.struct.pyramid.PyramidDiscrete;

/**
 * Creates an image pyramid by down sampling square regions using {@link AverageDownSampleOps}.
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class PyramidDiscreteAverage<T extends ImageGray> extends PyramidDiscrete<T> {

	/**
	 *
	 * @param imageType Type of image processed
	 * @param saveOriginalReference If a reference to the full resolution image should be saved instead of copied.
	 *                              Set to false if you don't know what you are doing.
	 * @param scaleFactors Scale factor for each layer in the pyramid relative to the input layer
	 */
	public PyramidDiscreteAverage(Class<T> imageType,
								  boolean saveOriginalReference, int... scaleFactors)
	{
		super(imageType,saveOriginalReference,scaleFactors);
	}

	@Override
	public void process(T input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN,
						ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO,
						FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG) {
		super.initialize(input.width,input.height);


		if (scale[0] == 1) {
			if (isSaveOriginalReference()) {
				setFirstLayer(input);
			} else {
				getLayer(0).setTo(input);
			}
		} else {
			AverageDownSampleOps.down(input, scale[0], getLayer(0));
		}

		for (int index = 1; index < getNumLayers(); index++) {
			int width = scale[index]/scale[index-1];
			AverageDownSampleOps.down(getLayer(index-1),width,getLayer(index));
		}
	}

	/**
	 * The center of the sampling kernel is 1/2 the square region's width
	 *
	 * @param layer Layer in the pyramid
	 * @return offset
	 */
	@Override
	public double getSampleOffset(int layer) {
		return (scale[layer]-1)/2.0;
	}

	@Override
	public double getSigma(int layer) {
		return 0;
	}
}
