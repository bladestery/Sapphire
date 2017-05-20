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

package boofcv.abst.feature.detdesc;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detdesc.DetectDescribeSurfPlanar;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
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
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.Planar;
import georegression.struct.point.Point2D_F64;

/**
 * Wrapper around {@link boofcv.alg.feature.detdesc.DetectDescribeSurfPlanar} for {@link DetectDescribePoint}.
 *
 * @param <T> Image band type
 * @param <II> Integral image type
 *
 * @author Peter Abeles
 */
public class SurfPlanar_to_DetectDescribePoint<T extends ImageGray, II extends ImageGray>
		implements DetectDescribePoint<Planar<T>,BrightFeature>
{
	private GeneralizedImageOps GIO;
	DetectDescribeSurfPlanar<II> alg;

	T gray;
	II grayII;
	Planar<II> bandII;

	public SurfPlanar_to_DetectDescribePoint(DetectDescribeSurfPlanar<II> alg ,
											 Class<T> imageType, Class<II> integralType)
	{
		this.alg = alg;

		gray = GIO.createSingleBand(imageType, 1, 1);
		grayII = GIO.createSingleBand(integralType,1,1);
		bandII = new Planar<>(integralType, 1, 1, alg.getDescribe().getNumBands());
	}

	@Override
	public void detect(Planar<T> input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF,
					   ConvertImage CI, UtilWavelet UW) {
		gray.reshape(input.width,input.height);
		grayII.reshape(input.width,input.height);
		bandII.reshape(input.width,input.height);

		GConvertImage.average(input,gray, ISC, CI);
		GIntegralImageOps.transform(gray, grayII, ISC, GIO);
		for( int i = 0; i < input.getNumBands(); i++)
			GIntegralImageOps.transform(input.getBand(i), bandII.getBand(i), ISC, GIO);

		alg.detect(grayII,bandII);
	}

	@Override
	public boolean hasScale() {
		return true;
	}

	@Override
	public boolean hasOrientation() {
		return true;
	}

	@Override
	public BrightFeature getDescription(int index) {
		return alg.getDescription(index);
	}

	@Override
	public BrightFeature createDescription() {
		return alg.createDescription();
	}

	@Override
	public Class<BrightFeature> getDescriptionType() {
		return BrightFeature.class;
	}

	@Override
	public int getNumberOfFeatures() {
		return alg.getNumberOfFeatures();
	}

	@Override
	public Point2D_F64 getLocation(int featureIndex) {
		return alg.getLocation(featureIndex);
	}

	@Override
	public double getRadius(int featureIndex) {
		return alg.getRadius(featureIndex);
	}

	@Override
	public double getOrientation(int featureIndex) {
		return alg.getOrientation(featureIndex);
	}
}
