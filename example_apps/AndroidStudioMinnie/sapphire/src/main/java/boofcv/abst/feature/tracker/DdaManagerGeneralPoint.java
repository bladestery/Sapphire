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

package boofcv.abst.feature.tracker;

import boofcv.abst.feature.describe.DescribeRegionPoint;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.feature.detect.interest.EasyGeneralFeatureDetector;
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
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.QueueCorner;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageGray;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point2D_I16;
import sapphire.compiler.FBFGenerator;
import sapphire.compiler.FIBAGenerator;

import org.ddogleg.struct.FastQueue;

/**
 * Detects simple features (corners and blobs) whose location if fully described by a pixel coordinate.  Unlike more
 * generalized implementations, previously detected features can be excluded automatically when detecting new
 * features.
 *
 * @author Peter Abeles
 */
public class DdaManagerGeneralPoint<I extends ImageGray, D extends ImageGray, Desc extends TupleDesc>
		implements DdaFeatureManager<I, Desc> {

	// feature detector
	private EasyGeneralFeatureDetector<I,D> detector;
	// feature descriptor
	private DescribeRegionPoint<I, Desc> describe;
	// scale that features should be created at
	private double scale;

	// storage for descriptors
	private FastQueue<Desc> descriptors;
	private FastQueue<Point2D_F64> locations = new FastQueue<>(100, Point2D_F64.class, true);

	public DdaManagerGeneralPoint(EasyGeneralFeatureDetector<I, D> detector,
								  DescribeRegionPoint<I, Desc> describe,
								  double scale) {
		this.detector = detector;
		this.describe = describe;
		this.scale = scale;

		descriptors = UtilFeature.createQueue(describe,100);
	}

	@Override
	public void detectFeatures(I input, FastQueue<Point2D_F64> locDst, FastQueue<Desc> featDst, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
							   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
							   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
							   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
							   ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV, FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF) {

		// detect features in the image
		detector.detect(input,null, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO
		);
		describe.setImage(input);

		QueueCorner found = detector.getMaximums();

		// compute descriptors and populate results list
		descriptors.reset();
		locations.reset();
		for( int i = 0; i < found.size; i++ ) {
			Point2D_I16 p = found.get(i);
			Desc desc = descriptors.grow();

			if( describe.process(p.x,p.y,0,scale,desc) ) {
				Point2D_F64 loc = locations.grow();
				loc.set(p.x,p.y);
				describe.process(loc.x,loc.y,0,scale,desc);
				featDst.add(desc);
				locDst.add( loc );
			} else {
				descriptors.removeTail();
			}
		}
	}

	@Override
	public Desc createDescription() {
		return describe.createDescription();
	}

	@Override
	public Class<Desc> getDescriptionType() {
		return describe.getDescriptionType();
	}
}
