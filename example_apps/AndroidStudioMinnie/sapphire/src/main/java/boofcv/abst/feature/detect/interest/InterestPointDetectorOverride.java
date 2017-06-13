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

import boofcv.abst.feature.orientation.OrientationImage;
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
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;

/**
 * Provides the capability to tack on a different algorithm for the feature's location, scale, and orientation.
 *
 * @author Peter Abeles
 */
public class InterestPointDetectorOverride< T extends ImageGray>
		implements InterestPointDetector<T>
{
	InterestPointDetector<T> detector;
	OrientationImage<T> orientation;

	/**
	 * Specifies which algorithms are to be used.  If orientation is specified then it will override the orientation
	 * provided by 'detector'
	 *
	 * @param detector Interest point detector and default scale and orientation.
	 * @param orientation If not null then this will be used to estimate the feature's orientation.
	 */
	public InterestPointDetectorOverride(InterestPointDetector<T> detector, OrientationImage<T> orientation) {
		this.detector = detector;
		this.orientation = orientation;
	}

	@Override
	public void detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF,
					   ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		detector.detect(input, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
		if( orientation != null )
			orientation.setImage(input, ISC, GIO, DHF, CINB, CJBG, GSO, GSUO, FKG, GIMO, IMO, CI, FIB, CNN, CNJB, CN, IT, FI);
	}

	@Override
	public int getNumberOfFeatures() {
		return detector.getNumberOfFeatures();
	}

	@Override
	public Point2D_F64 getLocation(int featureIndex) {
		return detector.getLocation(featureIndex);
	}

	@Override
	public double getRadius(int featureIndex) {
		return detector.getRadius(featureIndex);
	}

	@Override
	public double getOrientation(int featureIndex, FactoryKernelGaussian FKG, FastHessianFeatureDetector FHFD) {
		if( orientation == null )
			return detector.getOrientation(featureIndex, FKG, FHFD);

		Point2D_F64 p = detector.getLocation(featureIndex);
		orientation.setObjectRadius(getRadius(featureIndex), FKG);
		return orientation.compute(p.x,p.y, FHFD);
	}

	@Override
	public boolean hasScale() {
		return detector.hasScale();
	}

	@Override
	public boolean hasOrientation() {
		if( orientation == null )
			return detector.hasOrientation();
		return true;
	}
}
