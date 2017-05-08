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
import boofcv.core.image.border.BorderIndex1D_Extend;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.ImageBorder1D_F32;
import boofcv.core.image.border.ImageBorder1D_S32;
import boofcv.factory.filter.convolve.FactoryConvolve;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.convolve.Kernel1D;
import boofcv.struct.convolve.Kernel2D;
import boofcv.struct.image.ImageGray;
import boofcv.testing.BoofTesting;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Compares a specialized image derivative function to the equivalent convolution.
 * Designed to test functions from {@link HessianFromGradient}.
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class CompareHessianToConvolution {
	private static GBlurImageOps GBIO;
	private static GeneralizedImageOps GIO;
	private static InputSanityCheck ISC;
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
	FilterImageInterface outputFilters[] = new FilterImageInterface[2];

	Class<ImageGray> inputType;

	boolean processBorder;
	int borderSize = 0;

	public void setTarget( Method m )  {
		this.m = m;
		Class<?> []param = m.getParameterTypes();

		inputType = (Class<ImageGray>)param[0];
	}

	public void setKernel( int which , Kernel1D horizontal , Kernel1D vertical ) {
		FilterImageInterface<?,?> f1 = FactoryConvolve.convolve(horizontal,inputType,inputType, BorderType.EXTENDED,true);
		FilterImageInterface<?,?> f2 = FactoryConvolve.convolve(vertical,inputType,inputType, BorderType.EXTENDED,false);

		outputFilters[which] = new FilterSequence(f1,f2);

		if( borderSize < horizontal.getRadius() )
			borderSize = horizontal.getRadius();
		if( borderSize < vertical.getRadius() )
			borderSize = vertical.getRadius();
	}

	public void setKernel( int which , Kernel1D kernel , boolean isHorizontal) {
		outputFilters[which] =
				FactoryConvolve.convolve(kernel,inputType,inputType, BorderType.EXTENDED,isHorizontal);
		if( borderSize < kernel.getRadius() )
			borderSize = kernel.getRadius();
	}

	public void setKernel( int which , Kernel2D kernel ) {
		outputFilters[which] =
				FactoryConvolve.convolve(kernel,inputType,inputType, BorderType.EXTENDED);
		if( borderSize < kernel.getRadius() )
			borderSize = kernel.getRadius();
	}
	public void compare( ImageGray...images)  {
		compare(false,images);
		compare(true,images);
	}

	public void compare( boolean processBorder , ImageGray...images)  {
		this.processBorder = processBorder;
		innerCompare(images);

		ImageGray subOut[] = new ImageGray[ images.length ];
		for( int i = 0; i < images.length; i++ )
			subOut[i] = BoofTesting.createSubImageOf(images[i]);
		innerCompare(subOut);
	}



	protected void innerCompare( ImageGray...images)  {
		Class<?> []param = m.getParameterTypes();

		if( images.length != 5 )
			throw new RuntimeException("Unexpected number of outputImages passed in: "+images.length);

		int width = images[0].width;
		int height = images[0].height;

		// now compute the second derivative using provided convolution filters
		ImageGray expectedOutput[] = new ImageGray[3];
		for( int i = 0; i < expectedOutput.length; i++ ) {
			expectedOutput[i] = (ImageGray)images[0].createNew(width,height);
		}
		outputFilters[0].process(images[0],expectedOutput[0], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
		outputFilters[1].process(images[1],expectedOutput[1], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
		outputFilters[1].process(images[0],expectedOutput[2], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);

		// compute results from the test function
		Object testInputs[] = new Object[ param.length ];
		for( int i = 0; i < 5; i++ ) {
			testInputs[i] = images[i];
		}
		if( processBorder ) {
			if( images[0].getDataType().isInteger())
				testInputs[5] = new ImageBorder1D_S32(BorderIndex1D_Extend.class);
			else
				testInputs[5] = new ImageBorder1D_F32(BorderIndex1D_Extend.class);
		} else {
			testInputs[5] = null;
		}

		try {
			m.invoke(null,testInputs);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		// sanity check.  The derivatives should be the same

		// compare the results
		for( int i = 0; i < expectedOutput.length; i++ ) {
			int border = processBorder ? 0 : borderSize;
			BoofTesting.assertEqualsInner(expectedOutput[i], images[i + 2], 1e-4f, border, border, true);

			if( !processBorder )
				BoofTesting.checkBorderZero(images[i+2],border);
		}
	}

}
