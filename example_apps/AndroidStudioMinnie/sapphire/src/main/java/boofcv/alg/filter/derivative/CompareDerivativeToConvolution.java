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

package boofcv.alg.filter.derivative;

import boofcv.abst.filter.FilterImageInterface;
import boofcv.abst.filter.FilterSequence;
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
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.convolve.FactoryConvolve;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.convolve.Kernel1D;
import boofcv.struct.convolve.Kernel2D;
import boofcv.struct.image.ImageGray;
import boofcv.testing.BoofTesting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Compares a specialized image derivative function to the equivalent convolution
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class CompareDerivativeToConvolution {
	private static FactoryImageBorder FIB;
	private static GBlurImageOps GBIO;
	private static InputSanityCheck ISC;
	private static GeneralizedImageOps GIO;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ConvolveNormalized CN;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	Method m;
	FilterImageInterface outputFilters[];
	Border borders[];

	Class<ImageGray> inputType;
	Class<ImageGray> outputType;

	boolean processBorder;

	public void setTarget( Method m )  {
		this.m = m;
		Class<?> []param = m.getParameterTypes();
		outputFilters = new FilterImageInterface<?,?>[ param.length ];
		borders = new Border[ param.length ];

		inputType = (Class<ImageGray>)param[0];
		outputType = (Class<ImageGray>)param[1];
	}

	public void setKernel( int which , Kernel1D horizontal , Kernel1D vertical ) {
		FilterImageInterface<?,?> f1 = FactoryConvolve.convolve(horizontal,inputType,outputType, BorderType.EXTENDED,true);
		FilterImageInterface<?,?> f2 = FactoryConvolve.convolve(vertical,outputType,outputType, BorderType.EXTENDED,false);

		outputFilters[which] = new FilterSequence(f1,f2);
		borders[which] = setBorder(horizontal,vertical);

	}

	public void setKernel( int which , Kernel1D kernel , boolean isHorizontal) {
		outputFilters[which] =
				FactoryConvolve.convolve(kernel,inputType,outputType, BorderType.EXTENDED,isHorizontal);
		borders[which] = setBorder(kernel,isHorizontal);
	}

	public void setKernel( int which , Kernel2D kernel ) {
		outputFilters[which] = FactoryConvolve.convolve(kernel,inputType,outputType, BorderType.EXTENDED);
		borders[which] = setBorder(kernel);
	}
	public void compare(ImageGray inputImage , ImageGray...outputImages)  {
		compare(false,inputImage,outputImages);
		compare(true,inputImage,outputImages);
	}

	public void compare(boolean processBorder , ImageGray inputImage , ImageGray...outputImages)  {
		this.processBorder = processBorder;
		innerCompare(inputImage,outputImages);

		inputImage = BoofTesting.createSubImageOf(inputImage);
		ImageGray subOut[] = new ImageGray[ outputImages.length ];
		for( int i = 0; i < outputImages.length; i++ )
			subOut[i] = BoofTesting.createSubImageOf(outputImages[i]);
		innerCompare(inputImage,subOut);
	}

	protected void innerCompare(ImageGray inputImage , ImageGray...outputImages)  {
		Class<?> []param = m.getParameterTypes();
		int numImageOutputs = countImageOutputs(param);
		if( outputImages.length != numImageOutputs )
			throw new RuntimeException("Unexpected number of outputImages passed in");

		// declare and compute the validation results
		ImageGray expectedOutput[] = new ImageGray[param.length-2];
		for( int i = 0; i < expectedOutput.length; i++ ) {
			expectedOutput[i] = (ImageGray)outputImages[i].createNew(inputImage.width,inputImage.height);
			outputFilters[i].process(inputImage,expectedOutput[i], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
		}

		// compute results from the test function
		Object testInputs[] = new Object[ param.length ];
		testInputs[0] = inputImage;
		for( int i = 1; i < numImageOutputs+1; i++ ) {
			testInputs[i] = outputImages[i-1];
		}
		if( param.length == numImageOutputs + 2 ) {
			if( processBorder ) {
				testInputs[param.length-1] = FIB.single(inputImage.getClass(), BorderType.EXTENDED);
			} else {
				testInputs[param.length-1] = null;
			}

		} try {
			m.invoke(null,testInputs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		// assume the most extreme border is used
		int borderX0 = 0, borderY0 = 0, borderX1 = 0, borderY1 = 0;
		for( int i = 0; i < expectedOutput.length; i++ ) {
			Border b = borders[i];

			borderX0 = Math.max(borderX0,b.borderX0);
			borderX1 = Math.max(borderX1,b.borderX1);
			borderY0 = Math.max(borderY0,b.borderY0);
			borderY1 = Math.max(borderY1,b.borderY1);
		}

		// compare the results
		for( int i = 0; i < expectedOutput.length; i++ ) {
			BoofTesting.assertEqualsInner(expectedOutput[i], outputImages[i], 1e-4f,
					borderX0, borderY0, borderX1, borderY1, false);

			if( !processBorder )
				BoofTesting.checkBorderZero(outputImages[i],borderX0, borderY0, borderX1, borderY1);
		}
	}

	/**
	 * Counts the number of derivative images it takes in as an output.
	 * @param param
	 * @return
	 */
	private int countImageOutputs(Class<?>[] param) {

		int count = 0;

		for( int i = 1; i < param.length; i++ ) {
			if( ImageGray.class.isAssignableFrom(param[i])) {
				count++;
			}
		}

		return count;
	}

	private Border setBorder( Kernel2D kernel ) {
		Border b = new Border();
		b.borderX0 = b.borderX1 = b.borderY0 = b.borderY1 = kernel.getRadius();
		return b;
	}

	private Border setBorder( Kernel1D kernel , boolean isHorizontal ) {
		return setBorder(kernel, kernel);
		// just assume it is going to convolve both at the same time
//		Border b = new Border();
//		if( isHorizontal ) {
//			b.borderX0 = kernel.getOffset();
//			b.borderX1 = kernel.getWidth() - kernel.getOffset() - 1;
//			b.borderY0 = 0;
//			b.borderY1 = 0;
//		} else {
//			b.borderX0 = 0;
//			b.borderX1 = 0;
//			b.borderY0 = kernel.getOffset();
//			b.borderY1 = kernel.getWidth() - kernel.getOffset() - 1;
//		}
//		return b;
	}

	private Border setBorder( Kernel1D horizontal , Kernel1D vertical ) {
		Border b = new Border();
		b.borderX0 = horizontal.getOffset();
		b.borderX1 = horizontal.getWidth() - horizontal.getOffset() - 1;
		b.borderY0 = horizontal.getOffset();
		b.borderY1 = horizontal.getWidth() - horizontal.getOffset() - 1;

		if( horizontal.getWidth() != vertical.getWidth() )
			throw new RuntimeException("handle this case in the unit test");
		return b;
	}

	private static class Border {
		int borderX0 = 0;
		int borderY0 = 0;
		int borderX1 = 0;
		int borderY1 = 0;
	}
}
