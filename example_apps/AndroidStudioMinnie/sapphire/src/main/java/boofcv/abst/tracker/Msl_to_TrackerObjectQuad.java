/*
 * Copyright (c) 2011-2015, Peter Abeles. All Rights Reserved.
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

package boofcv.abst.tracker;

import boofcv.alg.InputSanityCheck;
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
import boofcv.alg.tracker.meanshift.PixelLikelihood;
import boofcv.alg.tracker.meanshift.TrackerMeanShiftLikelihood;
import boofcv.alg.transform.fft.DiscreteFourierTransformOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.geometry.UtilPolygons2D_F64;
import georegression.struct.shapes.Quadrilateral_F64;
import georegression.struct.shapes.Rectangle2D_F64;
import georegression.struct.shapes.RectangleLength2D_I32;

/**
 * Wrapper around {@link  boofcv.alg.tracker.meanshift.TrackerMeanShiftLikelihood} for {@link TrackerObjectQuad}
 *
 * @author Peter Abeles
 */
public class Msl_to_TrackerObjectQuad <T extends ImageBase> implements TrackerObjectQuad<T> {

	TrackerMeanShiftLikelihood<T> tracker;
	PixelLikelihood<T> likelihood;

	ImageType<T> type;

	Rectangle2D_F64 rect = new Rectangle2D_F64();
	RectangleLength2D_I32 target = new RectangleLength2D_I32();

	public Msl_to_TrackerObjectQuad(TrackerMeanShiftLikelihood<T> tracker,
									PixelLikelihood<T> likelihood , ImageType<T> imageType) {
		this.tracker = tracker;
		this.likelihood = likelihood;

		type = imageType;
	}

	@Override
	public boolean initialize(T image, Quadrilateral_F64 location, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							  FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
							  ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO,
							  GImageMiscOps GIMO, ImageMiscOps IMO, FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO,
							  GradientSobel_UnrolledOuter GSUO, FactoryPyramid FP, DiscreteFourierTransformOps DFTO, ImageType IT) {

		UtilPolygons2D_F64.bounding(location, rect);

		target.x0 = (int)rect.p0.x;
		target.y0 = (int)rect.p0.y;
		target.width = (int)rect.getWidth()+1;
		target.height = (int)rect.getHeight()+1;

		likelihood.setImage(image);
		likelihood.createModel(target);
		tracker.initialize(image,target, IMO);

		return true;
	}

	@Override
	public boolean process(T image, Quadrilateral_F64 location, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO,
						   GImageMiscOps GIMO, ImageMiscOps IMO, FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO,
						   GradientSobel_UnrolledOuter GSUO, FactoryPyramid FP, DiscreteFourierTransformOps DFTO, ImageType IT) {

		if( !tracker.process(image, IMO))
		    return false;

		RectangleLength2D_I32 rect = tracker.getLocation();
		UtilPolygons2D_F64.convert(rect,location);

		return true;
	}

	@Override
	public ImageType<T> getImageType() {
		return type;
	}
}
