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

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.dense.DescribeDenseSiftAlg;
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
import boofcv.alg.filter.derivative.GradientSobel;
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
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.feature.TupleDesc_F64;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_I32;
import sapphire.compiler.FDGenerator;

import java.util.List;

/**
 * High level wrapper around {@link DescribeDenseSiftAlg} for {@link DescribeImageDense}
 *
 * @author Peter Abeles
 */
public class DescribeImageDenseSift<T extends ImageGray, D extends ImageGray>
		implements DescribeImageDense<T,TupleDesc_F64>
{
	// dense SIFT implementation
	DescribeDenseSiftAlg<D> alg;

	// computes the image gradient
	ImageGradient<T,D> gradient;

	// type of input image
	ImageType<T> inputType;

	// period in pixels at scale of 1
	double periodX;
	double periodY;

	// storage for the rescaled input image and its derivatives
	D derivX;
	D derivY;

	/**
	 *
	 * @param alg Reference to the algorithm that is wrapped
	 * @param periodX How often the image is samples in pixels. X-axis
	 * @param periodY How often the image is samples in pixels. Y-axis
	 * @param inputType Type of input image
	 */
	public DescribeImageDenseSift(DescribeDenseSiftAlg<D> alg,
								  double periodX , double periodY ,
								  Class<T> inputType, ImageType IT, GeneralizedImageOps GIO, FactoryImageBorder FIB, FactoryDerivative FD) {
		this.alg = alg;
		this.periodX = periodX;
		this.periodY = periodY;
		this.inputType = IT.single(inputType);

		Class<D> gradientType = alg.getDerivType();
		gradient = FD.three(inputType,gradientType, GIO, FIB);

		derivX = GIO.createSingleBand(gradientType,1,1);
		derivY = GIO.createSingleBand(gradientType,1,1);
	}

	@Override
	public void process(T input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
						ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
						ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB, ImageType IT, FactoryInterpolation FI) {
		alg.setPeriodColumns(periodX);
		alg.setPeriodRows(periodY);

		derivX.reshape(input.width,input.height);
		derivY.reshape(input.width,input.height);
		gradient.process(input,derivX,derivY, ISC,DHF, CINB, CJBG,GSO,GSUO);

		alg.setImageGradient(derivX,derivY, ISC);
		alg.process();
	}

	@Override
	public List<TupleDesc_F64> getDescriptions() {
		return alg.getDescriptors().toList();
	}

	@Override
	public List<Point2D_I32> getLocations() {
		return alg.getLocations().toList();
	}

	@Override
	public ImageType<T> getImageType() {
		return inputType;
	}

	@Override
	public TupleDesc_F64 createDescription() {
		return new TupleDesc_F64(alg.getDescriptorLength());
	}

	@Override
	public Class<TupleDesc_F64> getDescriptionType() {
		return TupleDesc_F64.class;
	}

	public DescribeDenseSiftAlg<D> getAlg() {
		return alg;
	}
}
