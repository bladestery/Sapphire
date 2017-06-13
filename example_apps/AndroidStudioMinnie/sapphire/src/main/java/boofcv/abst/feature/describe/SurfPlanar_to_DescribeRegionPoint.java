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
import boofcv.alg.feature.describe.DescribePointSurfPlanar;
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
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.BoofDefaults;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import sapphire.compiler.GIOGenerator;

/**
 * Wrapper around {@link DescribePointSurfPlanar} for {@link DescribeRegionPoint}
 *
 * @param <T> Image band type
 * @param <II> Integral image type
 *
 * @author Peter Abeles
 */
public class SurfPlanar_to_DescribeRegionPoint<T extends ImageGray, II extends ImageGray>
	implements DescribeRegionPoint<Planar<T>,BrightFeature>
{
	DescribePointSurfPlanar<II> alg;

	T gray;
	II grayII;
	Planar<II> bandII;

	ImageType<Planar<T>> imageType;

	public SurfPlanar_to_DescribeRegionPoint(DescribePointSurfPlanar<II> alg,
											 Class<T> imageType, Class<II> integralType, ImageType IT, GeneralizedImageOps GIO) {
		this.alg = alg;

		gray = GIO.createSingleBand(imageType, 1, 1);
		grayII = GIO.createSingleBand(integralType,1,1);
		bandII = new Planar<>(integralType, 1, 1, alg.getNumBands());

		this.imageType = IT.pl(alg.getNumBands(), imageType);
	}

	@Override
	public void setImage(Planar<T> image, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
						 ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
						 ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						 ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB, ImageType IT, FactoryInterpolation FI) {
		gray.reshape(image.width,image.height);
		grayII.reshape(image.width,image.height);
		bandII.reshape(image.width,image.height);

		GConvertImage.average(image, gray, ISC, CI);
		GIntegralImageOps.transform(gray, grayII, ISC, GIO);
		for( int i = 0; i < image.getNumBands(); i++)
			GIntegralImageOps.transform(image.getBand(i), bandII.getBand(i), ISC, GIO);

		alg.setImage(grayII,bandII);
	}

	@Override
	public boolean process(double x, double y, double orientation, double radius, BrightFeature description) {

		alg.describe(x,y,orientation, radius/ BoofDefaults.SURF_SCALE_TO_RADIUS,description);

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
	public ImageType<Planar<T>> getImageType() {
		return imageType;
	}

	@Override
	public double getCanonicalWidth() {
		return alg.getDescribe().getCanonicalWidth();
	}

	@Override
	public BrightFeature createDescription() {
		return alg.createDescription();
	}

	@Override
	public Class<BrightFeature> getDescriptionType() {
		return BrightFeature.class;
	}
}
