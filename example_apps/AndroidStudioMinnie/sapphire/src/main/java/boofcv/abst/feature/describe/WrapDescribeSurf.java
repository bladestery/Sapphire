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
import boofcv.alg.feature.describe.DescribePointSurf;
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
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.ii.GIntegralImageOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.BoofDefaults;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;


/**
 * @author Peter Abeles
 */
public class WrapDescribeSurf<T extends ImageGray, II extends ImageGray>
		implements DescribeRegionPoint<T,BrightFeature> {
	// computes SURF feature descriptor
	DescribePointSurf<II> surf;
	// integral image
	II ii;

	ImageType<T> imageType;

	public WrapDescribeSurf(DescribePointSurf<II> surf , Class<T> imageType, ImageType IT)
	{
		this.surf = surf;
		this.imageType = IT.single(imageType);
	}

	@Override
	public BrightFeature createDescription() {
		return new BrightFeature(surf.getDescriptionLength());
	}

	@Override
	public void setImage(T image, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
						 ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
						 ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						 ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB, ImageType IT, FactoryInterpolation FI) {
		if( ii != null ) {
			ii.reshape(image.width,image.height);
		}

		// compute integral image
		ii = GIntegralImageOps.transform(image,ii, ISC, GIO);
		surf.setImage(ii);
	}

	@Override
	public boolean process(double x, double y, double orientation , double radius, BrightFeature storage) {

		surf.describe(x,y, orientation, radius/ BoofDefaults.SURF_SCALE_TO_RADIUS, storage);

		return true;
	}

	@Override
	public boolean requiresRadius() {
		return true;
	}

	@Override
	public boolean requiresOrientation() {
		return true;
	}

	@Override
	public ImageType<T> getImageType() {
		return imageType;
	}

	@Override
	public double getCanonicalWidth() {
		return surf.getCanonicalWidth();
	}

	@Override
	public Class<BrightFeature> getDescriptionType() {
		return BrightFeature.class;
	}
}
