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

package boofcv.abst.feature.describe;

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
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * Converts the region descriptor type from the {@link DescribeRegionPoint} into the desired output using a
 * {@link ConvertTupleDesc}.
 *
 * @author Peter Abeles
 */
public class DescribeRegionPointConvert<T extends ImageGray,In extends TupleDesc,Out extends TupleDesc>
	implements DescribeRegionPoint<T,Out>
{
	// Computers the description
	DescribeRegionPoint<T,In> original;
	// Change the description type
	ConvertTupleDesc<In,Out> converter;

	// internal storage for the original descriptor
	In storage;

	public DescribeRegionPointConvert(DescribeRegionPoint<T, In> original,
									  ConvertTupleDesc<In, Out> converter) {
		this.original = original;
		this.converter = converter;

		storage = original.createDescription();
	}

	@Override
	public void setImage(T image, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						 ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						 ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						 ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF,
						 GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB) {
		original.setImage(image, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, DHF, GSO, GSUO, FIB);
	}

	@Override
	public Out createDescription() {
		return converter.createOutput();
	}

	@Override
	public boolean process(double x, double y, double orientation, double radius, Out storage) {

		if( !original.process(x,y,orientation, radius, this.storage) )
			return false;
		converter.convert(this.storage, storage);

		return true;
	}

	@Override
	public boolean requiresRadius() {
		return original.requiresRadius();
	}

	@Override
	public boolean requiresOrientation() {
		return original.requiresOrientation();
	}

	@Override
	public ImageType<T> getImageType() {
		return original.getImageType();
	}

	@Override
	public double getCanonicalWidth() {
		return original.getCanonicalWidth();
	}

	@Override
	public Class<Out> getDescriptionType() {
		return converter.getOutputType();
	}
}
