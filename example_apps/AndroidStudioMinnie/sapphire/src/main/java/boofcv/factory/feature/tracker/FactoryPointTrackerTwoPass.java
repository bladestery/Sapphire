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

package boofcv.factory.feature.tracker;

import boofcv.abst.feature.associate.AssociateDescription2D;
import boofcv.abst.feature.describe.DescribeRegionPoint;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.*;
import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.feature.detect.interest.EasyGeneralFeatureDetector;
import boofcv.alg.feature.detect.interest.GeneralFeatureDetector;
import boofcv.alg.interpolate.InterpolateRectangle;
import boofcv.alg.tracker.klt.PkltConfig;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.factory.feature.detect.intensity.FactoryIntensityPointAlg;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.pyramid.PyramidDiscrete;

/**
 * @author Peter Abeles
 */
public class FactoryPointTrackerTwoPass {
	/**
	 * Pyramid KLT feature tracker.
	 *
	 * @param config Config for the tracker. Try PkltConfig.createDefault().
	 * @param configExtract Configuration for extracting features
	 * @return KLT based tracker.
	 */
	public static <I extends ImageGray, D extends ImageGray>
	PointTrackerTwoPass<I> klt(PkltConfig config, ConfigGeneralDetector configExtract,
							   Class<I> imageType, Class<D> derivType, GeneralizedImageOps GIO, FactoryKernelGaussian FKG, FactoryFeatureExtractor FFE,
							   FactoryIntensityPointAlg FIPA, FactoryPyramid FP, FactoryDerivative FD, FactoryImageBorder FIB, ImageType IT, FactoryPointTracker FPT,
							   FactoryInterpolation FI) {

		GeneralFeatureDetector<I, D> detector = FPT.createShiTomasi(configExtract, derivType, GIO, FKG, FFE, FIPA);

		InterpolateRectangle<I> interpInput = FI.<I>bilinearRectangle(imageType);
		InterpolateRectangle<D> interpDeriv = FI.<D>bilinearRectangle(derivType);

		ImageGradient<I,D> gradient = FD.sobel(imageType, derivType, GIO, FIB);

		PyramidDiscrete<I> pyramid = FP.discreteGaussian(config.pyramidScaling,-1,2,true,imageType, FKG);

		return new PointTrackerTwoPassKltPyramid<>(config.config, config.templateRadius, pyramid, detector,
				gradient, interpInput, interpDeriv, IT);
	}

	public static <I extends ImageGray, D extends ImageGray, Desc extends TupleDesc>
	PointTrackerTwoPass<I> dda(GeneralFeatureDetector<I, D> detector,
							   DescribeRegionPoint<I, Desc> describe,
							   AssociateDescription2D<Desc> associate1,
							   AssociateDescription2D<Desc> associate2,
							   double scale,
							   Class<I> imageType, FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB) {

		EasyGeneralFeatureDetector<I,D> easy = new EasyGeneralFeatureDetector<>(detector, imageType, null, FD, GIO, FIB);

		DdaManagerGeneralPoint<I,D,Desc> manager = new DdaManagerGeneralPoint<>(easy, describe, scale);

		if( associate2 == null )
			associate2 = associate1;

		return new DetectDescribeAssociateTwoPass<>(manager, associate1, associate2, false);
	}

	public static <I extends ImageGray, Desc extends TupleDesc>
	PointTrackerTwoPass<I> dda(DetectDescribePoint<I,Desc> detectDescribe,
							   AssociateDescription2D<Desc> associate1 ,
							   AssociateDescription2D<Desc> associate2 ,
							   boolean updateDescription ) {


		DdaManagerDetectDescribePoint<I,Desc> manager = new DdaManagerDetectDescribePoint<>(detectDescribe);

		if( associate2 == null )
			associate2 = associate1;

		return new DetectDescribeAssociateTwoPass<>(manager, associate1, associate2, updateDescription);
	}
}
