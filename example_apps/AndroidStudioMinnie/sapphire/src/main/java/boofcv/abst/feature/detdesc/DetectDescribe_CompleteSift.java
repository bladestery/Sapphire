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
import boofcv.alg.feature.detdesc.CompleteSift;
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
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.BrightFeature;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;
import georegression.struct.point.Point2D_F64;
import sapphire.compiler.FIBAGenerator;

/**
 * Wrapper around {@link CompleteSift} for {@link DetectDescribePoint}.
 *
 * @author Peter Abeles
 */
public class DetectDescribe_CompleteSift<In extends ImageGray>
		implements DetectDescribePoint<In,BrightFeature> {

	CompleteSift alg;
	GrayF32 imageFloat = new GrayF32(1,1);

	public DetectDescribe_CompleteSift(CompleteSift alg) {
		this.alg = alg;
	}

	@Override
	public BrightFeature createDescription() {
		return new BrightFeature(alg.getDescriptorLength());
	}

	@Override
	public BrightFeature getDescription(int index) {
		return alg.getDescriptions().data[index];
	}

	@Override
	public Class<BrightFeature> getDescriptionType() {
		return BrightFeature.class;
	}

	@Override
	public void detect(In input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB) {
		if( input instanceof GrayF32)
			alg.process((GrayF32)input, FHFD, FIB, ISC, CNN, CINB, CNJB, CN);
		else {
			imageFloat.reshape(input.width,input.height);
			GConvertImage.convert(input,imageFloat, ISC, GIO, GIMO, IMO);
			alg.process(imageFloat, FHFD, FIB, ISC, CNN, CINB, CNJB, CN);
		}
	}

	@Override
	public int getNumberOfFeatures() {
		return alg.getDescriptions().size;
	}

	@Override
	public Point2D_F64 getLocation(int featureIndex) {
		return alg.getLocations().get(featureIndex);
	}

	@Override
	public double getRadius(int featureIndex) {
		return alg.getLocations().get(featureIndex).scale;
	}

	@Override
	public double getOrientation(int featureIndex) {
		return alg.getOrientations().get(featureIndex);
	}

	@Override
	public boolean hasScale() {
		return true;
	}

	@Override
	public boolean hasOrientation() {
		return true;
	}
}
