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
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I16;
import org.ddogleg.struct.FastQueue;

/**
 * Allows a {@link GeneralFeatureDetector} to be used inside a {@link DetectorInterestPointMulti}.  The number of sets
 * depend upon if minimums and/or maximums are found by the detector.
 *
 * @author Peter Abeles
 */
public class GeneralToInterestMulti<T extends ImageGray, D extends ImageGray>
		implements DetectorInterestPointMulti<T>
{
	// point detector configured to detect minimums and maximums
	protected EasyGeneralFeatureDetector<T,D> detector;

	// scale of detected objects
	protected double radius;

	// list of found points
	protected FastQueue<Point2D_F64> foundMin = new FastQueue<>(10, Point2D_F64.class, true);
	protected FastQueue<Point2D_F64> foundMax = new FastQueue<>(10, Point2D_F64.class, true);

	// sets for each type of detected feature
	protected FoundPointSO sets[];

	public GeneralToInterestMulti(GeneralFeatureDetector<T, D> detector,
								  double radius,
								  Class<T> imageType, Class<D> derivType, FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB ) {
		this.detector = new EasyGeneralFeatureDetector<>(detector, imageType, derivType, FD, GIO, FIB);
		this.radius = radius;

		if( detector.isDetectMinimums() && detector.isDetectMaximums()) {
			sets = new FoundPointSO[]{new FoundMin(), new FoundMax()};
		} else if( detector.isDetectMaximums() ){
			sets = new FoundPointSO[]{new FoundMax()};
		} else if( detector.isDetectMinimums()) {
			sets = new FoundPointSO[]{new FoundMin()};
		}
	}

	@Override
	public void detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO, ConvertImage CI, UtilWavelet UW, ImageType IT) {
		foundMin.reset();
		foundMax.reset();

		detector.detect(input,null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, CI, UW, IT);

		QueueCorner min = detector.getMinimums();
		for( int i = 0; i < min.size; i++ ) {
			Point2D_I16 p = min.get(i);
			foundMin.grow().set(p.x,p.y);
		}
		QueueCorner max = detector.getMaximums();
		for( int i = 0; i < max.size; i++ ) {
			Point2D_I16 p = max.get(i);
			foundMax.grow().set(p.x,p.y);
		}
	}

	@Override
	public int getNumberOfSets() {
		return sets.length;
	}

	@Override
	public FoundPointSO getFeatureSet(int set) {
		return sets[set];
	}

	private class FoundMin implements FoundPointSO {
		@Override
		public int getNumberOfFeatures() {
			return foundMin.size;
		}

		@Override
		public Point2D_F64 getLocation(int featureIndex) {
			return foundMin.get(featureIndex);
		}

		@Override
		public double getRadius(int featureIndex) {
			return radius;
		}

		@Override
		public double getOrientation(int featureIndex, FactoryKernelGaussian FKG, FastHessianFeatureDetector FHFD) {
			return 0;
		}
	}

	private class FoundMax implements FoundPointSO {
		@Override
		public int getNumberOfFeatures() {
			return foundMax.size;
		}

		@Override
		public Point2D_F64 getLocation(int featureIndex) {
			return foundMax.get(featureIndex);
		}

		@Override
		public double getRadius(int featureIndex) {
			return radius;
		}

		@Override
		public double getOrientation(int featureIndex, FactoryKernelGaussian FKG, FastHessianFeatureDetector FHFD) {
			return 0;
		}
	}
}
