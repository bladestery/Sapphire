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

package boofcv.abst.feature.describe;

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
import boofcv.alg.filter.derivative.GradientSobel;
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
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.FactoryImage;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;


/**
 * Computes a description of the local region around a point at different circular radii and orientations.  The radius
 * specifies the size of the object and it should be assumed to be circular in shape.  Orientation rotates the
 * sample points.  Exactly how and if scale and orientation are used is implementation specific.
 *
 * @author Peter Abeles
 */
public interface DescribeRegionPoint<T extends ImageBase, Desc extends TupleDesc>
	extends DescriptorInfo<Desc>
{
	/**
	 * Specified the image which is to be processed.
	 *
	 * @param image The image which contains the features.
	 */
	void setImage(T image, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
				  ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
				  ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
				  ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF,
				  GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB, ImageType IT, FactoryInterpolation FI);

	/**
	 * Extract a description of the local image at the given point, scale, and orientation.
	 *
	 * WARNING: Check the returned value to make sure a description was actually computed.  Some implementations
	 * might now allow features to extend outside the image border and will return false.
	 *
	 * @param x Coordinate of the point.
	 * @param y Coordinate of the point.
	 * @param orientation Direction the feature is pointing at in radians. 0 = x-axis PI/2 = y-axis
	 * @param radius Radius of the detected object in pixels.
	 * @param description (output) Storage for extracted feature.  Use {@link #createDescription} to create descriptor.
	 * @return true if a descriptor can computed or false if not.
	 */
	boolean process( double x , double y , double orientation , double radius , Desc description );

	/**
	 * If size information is used when computing the descriptor.
	 *
	 * @return true is the radius is used when computing the descriptor or false if not
	 * @deprecated Likely to be removed in the near future.  if this flag is true or not won't change the input it
	 * gets when paired with {@link boofcv.abst.feature.detect.interest.InterestPointDetector}
	 */
	boolean requiresRadius();

	/**
	 * True if the descriptor uses orientation information.
	 *
	 * @deprecated Likely to be removed in the near future.  if this flag is true or not won't change the input it
	 * gets when paired with {@link boofcv.abst.feature.detect.interest.InterestPointDetector}
	 * @return if orientation needs to be provided or not
	 */
	boolean requiresOrientation();

	/**
	 * Description of the type of image it can process
	 *
	 * @return ImageDataType
	 */
	ImageType<T> getImageType();

	/**
	 * Returns the width of the square (or approximation of) sample region at a scale of one.
	 * When multiplied by the scale, pixels outside of the square region should not influence
	 * the descriptor's value.
	 *
	 * @return width of descriptor at a scale of one
	 */
	double getCanonicalWidth();
}
