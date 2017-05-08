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
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;


/**
 * Applies a sequence of filters. After the first filter each filter will have the same input
 * and output image type.
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class FilterSequence<Input extends ImageGray, Output extends ImageGray>
		implements FilterImageInterface<Input,Output> {

	FilterImageInterface<Input,Output> firstFilter;
	FilterImageInterface<Output,Output> sequence[];

	int borderHorizontal = 0;
	int borderVertical = 0;

	public FilterSequence( FilterImageInterface<Input,Output> first,
						   FilterImageInterface<Output,Output>... sequence )
	{
		this.firstFilter = first;
		this.sequence = sequence;

		if( first.getHorizontalBorder() > borderHorizontal)
			borderHorizontal = first.getHorizontalBorder();
		if( first.getVerticalBorder() > borderVertical)
			borderVertical = first.getVerticalBorder();

		for( FilterImageInterface<Output,Output> f : sequence ) {
			if( f.getHorizontalBorder() > borderHorizontal )
				borderHorizontal = f.getHorizontalBorder();
			if( f.getVerticalBorder() > borderVertical )
				borderVertical = f.getVerticalBorder();
		}
	}

	@Override
	public void process(Input input, Output output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO) {
		Output temp1 = (Output)output.createNew( output.width , output.height );
		Output temp2 = (Output)output.createNew( output.width , output.height );

		firstFilter.process(input,temp1, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);

		for( FilterImageInterface<Output,Output> f : sequence ) {
			f.process(temp1,temp2, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
			Output swap = temp1;
			temp1 = temp2;
			temp2 = swap;
			GIMO.fill(temp2, 0, IMO);
		}

		output.setTo(temp1);
	}

	@Override
	public int getHorizontalBorder() {
		return borderHorizontal;
	}

	@Override
	public int getVerticalBorder() {
		return borderVertical;
	}

	@Override
	public ImageType<Input> getInputType(ImageType IT) {
		return firstFilter.getInputType(IT);
	}

	@Override
	public ImageType<Output> getOutputType(ImageType IT) {
		return firstFilter.getOutputType(IT);
	}
}
