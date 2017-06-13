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

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.abst.filter.derivative.ImageHessian;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.EasyGeneralFeatureDetector;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.feature.detect.interest.GeneralFeatureDetector;
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
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I16;
import sapphire.compiler.FIBAGenerator;

import org.ddogleg.struct.FastQueue;

/**
 * Wrapper around {@link boofcv.alg.feature.detect.interest.GeneralFeatureDetector} to make it compatible with {@link InterestPointDetector}.
 *
 * @param <T> Input image type.
 * @param <D> Image derivative type.
 *
 * @author Peter Abeles
 */
public class GeneralToInterestPoint<T extends ImageGray, D extends ImageGray>
		extends EasyGeneralFeatureDetector<T,D>
		implements InterestPointDetector<T>
{

	double radius;

	// list of points it found
	protected FastQueue<Point2D_F64> foundPoints = new FastQueue<>(10, Point2D_F64.class, true);

	public GeneralToInterestPoint(GeneralFeatureDetector<T, D> detector,
								  double radius,
								  Class<T> imageType, Class<D> derivType, FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		super(detector,imageType,derivType, FD, GIO, FIB);
		this.radius = radius;
	}

	public GeneralToInterestPoint(GeneralFeatureDetector<T, D> detector,
								  ImageGradient<T, D> gradient,
								  ImageHessian<D> hessian,
								  double radius,
								  Class<D> derivType, GeneralizedImageOps GIO) {
		super(detector, gradient, hessian, derivType, GIO);
		this.radius = radius;
	}

	@Override
	public void detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF,
					   ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		super.detect(input,null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, CI, UW, IT);

		foundPoints.reset();
		if( getDetector().isDetectMaximums() ) {
			QueueCorner corners = detector.getMaximums();

			for (int i = 0; i < corners.size; i++) {
				Point2D_I16 p = corners.get(i);
				foundPoints.grow().set(p.x,p.y);
			}
		}

		if( getDetector().isDetectMinimums() ) {
			QueueCorner corners = detector.getMinimums();

			for (int i = 0; i < corners.size; i++) {
				Point2D_I16 p = corners.get(i);
				foundPoints.grow().set(p.x,p.y);
			}
		}

	}

	@Override
	public int getNumberOfFeatures() {
		return foundPoints.size();
	}

	@Override
	public Point2D_F64 getLocation(int featureIndex) {
		return foundPoints.get(featureIndex);
	}

	@Override
	public double getRadius(int featureIndex) {
		return radius;
	}

	@Override
	public double getOrientation(int featureIndex, FactoryKernelGaussian FKG, FastHessianFeatureDetector FHFD) {
		return 0;
	}

	@Override
	public boolean hasScale() {
		return false;
	}

	@Override
	public boolean hasOrientation() {
		return false;
	}
}
