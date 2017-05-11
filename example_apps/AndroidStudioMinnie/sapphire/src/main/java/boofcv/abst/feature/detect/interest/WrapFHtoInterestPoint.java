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

package boofcv.abst.feature.detect.interest;

import boofcv.alg.InputSanityCheck;
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
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.BoofDefaults;
import boofcv.struct.feature.ScalePoint;
import boofcv.struct.image.ImageGray;
import georegression.struct.point.Point2D_F64;

import java.util.List;


/**
 * Wrapper around {@link boofcv.alg.feature.detect.interest.FastHessianFeatureDetector} for {@link InterestPointDetector}.
 *
 * @author Peter Abeles
 */
public class WrapFHtoInterestPoint<T extends ImageGray, II extends ImageGray> implements InterestPointDetector<T> {

	// detects the feature's location and scale
	FastHessianFeatureDetector<II> detector;
	List<ScalePoint> location;
	II integral;

	public WrapFHtoInterestPoint(FastHessianFeatureDetector<II> detector) {
		this.detector = detector;
	}

	@Override
	public void detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB) {
		if( integral != null ) {
			integral.reshape(input.width,input.height);
		}

		integral = GIntegralImageOps.transform(input,integral, ISC, GIO);

		detector.detect(integral, FIBA, IBV);

		location = detector.getFoundPoints();
	}

	@Override
	public int getNumberOfFeatures() {
		return location.size();
	}

	@Override
	public Point2D_F64 getLocation(int featureIndex) {
		return location.get(featureIndex);
	}

	@Override
	public double getRadius(int featureIndex) {
		return location.get(featureIndex).scale* BoofDefaults.SURF_SCALE_TO_RADIUS;
	}

	@Override
	public double getOrientation(int featureIndex) {
		return 0;
	}

	@Override
	public boolean hasScale() {
		return true;
	}

	@Override
	public boolean hasOrientation() {
		return false;
	}
}
