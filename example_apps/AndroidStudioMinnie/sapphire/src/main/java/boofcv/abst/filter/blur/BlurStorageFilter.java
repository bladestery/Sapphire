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

package boofcv.abst.filter.blur;

import android.renderscript.ScriptGroup;

import boofcv.alg.InputSanityCheck;
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
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import sapphire.app.SapphireObject;

/**
 * Simplified interface for using a blur filter that requires storage.  Reflections are used to look up a function inside
 * of {@link boofcv.alg.filter.blur.BlurImageOps} which is then invoked later on.
 *
 * @author Peter Abeles
 */
public class BlurStorageFilter<T extends ImageGray> implements BlurFilter<T>, SapphireObject {
	// Wrapper around performed operation
	private BlurOperation operation;

	// the Gaussian's standard deviation
	private double sigma;
	// size of the blur region
	private int radius;
	// stores intermediate results
	private ImageGray storage;

	// type of image it processes
	Class<T> inputType;

	public BlurStorageFilter( String functionName , Class<T> inputType, int radius, GeneralizedImageOps GIO) {
		this(functionName,inputType,-1,radius, GIO);
	}

	public BlurStorageFilter( String functionName , Class<T> inputType, double sigma , int radius, GeneralizedImageOps GIO) {
		this.radius = radius;
		this.sigma = sigma;
		this.inputType = inputType;

		if( functionName.equals("mean")) {
			operation = new MeanOperation();
			storage = GIO.createSingleBand(inputType,1,1);
		} else if( functionName.equals("gaussian")) {
			operation = new GaussianOperation();
			storage = GIO.createSingleBand(inputType,1,1);
		} else if( functionName.equals("median")) {
			operation = new MedianOperator();
		} else {
			throw new IllegalArgumentException("Unknown function "+functionName);
		}

	}

	/**
	 * Radius of the square region.  The width is defined as the radius*2 + 1.
	 *
	 * @return Blur region's radius.
	 */
	@Override
	public int getRadius() {
		return radius;
	}

	@Override
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void process(T input, T output, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM) {
		if( storage != null )
			storage.reshape(output.width, output.height);
		operation.process(input,output, ISC, GBIO, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM);
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
		return IT.single(inputType);
	}

	@Override
	public ImageType<T> getOutputType(ImageType IT) {
		return IT.single(inputType);
	}

	public interface BlurOperation {
		public void process(ImageBase input , ImageBase output, InputSanityCheck ISC, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO,
							ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
							ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM);
	}

	public class MeanOperation implements BlurOperation, SapphireObject {
		@Override
		public void process(ImageBase input, ImageBase output, InputSanityCheck ISC, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB,
							ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM) {
			GBIO.mean(input,output,radius,storage, GBIO, GIO, ISC, BIO, CIM, IMHI, IMSEN, IMSN, CN, CNN, CINB, CNJB, ICM);
		}
	}

	public class GaussianOperation implements BlurOperation, SapphireObject {
		@Override
		public void process(ImageBase input, ImageBase output, InputSanityCheck ISC, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
		FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB,
							ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM) {
			GBIO.gaussian(input,output,sigma,radius,storage, ISC, GIO, GBIO, BIO, FKG, CN, CNN, CINB, CNJB);
		}
	}

	public class MedianOperator implements BlurOperation, SapphireObject {
		@Override
		public void process(ImageBase input, ImageBase output, InputSanityCheck ISC, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB,
							ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM) {
			GBIO.median(input,output,radius, GBIO, GIO, ISC, BIO, IMHI, IMSEN, IMSN);
		}
	}
}
