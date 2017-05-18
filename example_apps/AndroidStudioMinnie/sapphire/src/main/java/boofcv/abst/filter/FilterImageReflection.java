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
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
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
import boofcv.testing.BoofTesting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Turns functions into implementations of {@link FilterImageInterface} Wraps around any function which has two images as input and output.
 */
public class FilterImageReflection<Input extends ImageGray, Output extends ImageGray>
		implements FilterImageInterface<Input, Output> {
	// method being invoke for the filter
	Method m;

	// input and output image types
	Class<Input> inputType;
	Class<Output> outputType;
	// size of horizontal and vertical borders
	int borderX;
	int borderY;

	public FilterImageReflection(Class owner, String methodName, int borderX, int borderY, Class<Input> inputType, Class<Output> outputType) {
		try {
			this.m = BoofTesting.findMethod(owner, methodName, inputType, outputType);
		} catch( RuntimeException e ) {
			this.m = BoofTesting.findMethod(owner, methodName, inputType,int.class, outputType);
		}
		this.inputType = inputType;
		this.outputType = outputType;
		this.borderX = borderX;
		this.borderY = borderY;

		// sanity check
		Class param[] = m.getParameterTypes();
		if (param.length != 2 && param.length != 3 )
			throw new IllegalArgumentException("Input method must have two or three inputs");
		if (!ImageGray.class.isAssignableFrom(param[0]) || !ImageGray.class.isAssignableFrom(param[0]))
			throw new IllegalArgumentException("TWo input parameters must be of type ImageGray");
	}


	public FilterImageReflection(Method m, int borderX, int borderY, Class<Input> inputType, Class<Output> outputType) {
		this.m = m;
		this.inputType = inputType;
		this.outputType = outputType;
		this.borderX = borderX;
		this.borderY = borderY;

		// sanity check
		Class param[] = m.getParameterTypes();
		if (param.length != 2 && param.length != 3 )
			throw new IllegalArgumentException("Input method must have two or three inputs");
		if (!ImageGray.class.isAssignableFrom(param[0]) || !ImageGray.class.isAssignableFrom(param[0]))
			throw new IllegalArgumentException("TWo input parameters must be of type ImageGray");
	}

	@Override
	public void process(Input input, Output output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG) {
		if (input == null)
			throw new IllegalArgumentException("Input parameter is null");
		if (output == null)
			throw new IllegalArgumentException("Output parameter is null");

		try {
			if( m.getParameterTypes().length == 3 ) {
				m.invoke(null, input, 1 , output);
			} else {
				m.invoke(null, input, output);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getHorizontalBorder() {
		return borderX;
	}

	@Override
	public int getVerticalBorder() {
		return borderY;
	}

	@Override
	public ImageType<Input> getInputType(ImageType IT) {
		return IT.single(inputType);
	}

	@Override
	public ImageType<Output> getOutputType(ImageType IT) {
		return IT.single(outputType);
	}
}
