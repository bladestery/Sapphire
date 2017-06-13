/*
 * Copyright (c) 2011-2013, Peter Abeles. All Rights Reserved.
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
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;

/**
 * <p>
 * Extension of {@link PointTracker} allows for predictions of a feature's location to be incorporated into the tracker.
 * A typical usage would be to first run the tracker, estimate a motion model, then use the said motion model to
 * predict each feature's location.
 * </p>
 *
 * <p>
 * The behavior of {@link #process(boofcv.struct.image.ImageBase)}} has been changed.  It will only update each track's
 * location and the active list. {@link #performSecondPass()} will also only update the track's location and
 * active list.  {@link #finishTracking()} will update the dropped list and change the track's description.
 * </p>
 *
 * <p>
 * Dropping tracks: Tracks should not be dropped until after finishTracking() has been called.  If a track is
 * dropped between process() and finishTracking() are called, then the behavior is not defined.
 * </p>
 *
 * <p>
 * NOTES:
 * <ol>
 * <li> A track hint can be set before {@link #process(boofcv.struct.image.ImageBase)} is called. </li>
 * <li> Calling {@link #process(boofcv.struct.image.ImageBase)} and {@link #finishTracking()} is equivalent
 * to just calling process() in the standard {@link PointTracker} interface.</li>
 * <ol>
 * </p>
 *
 * @author Peter Abeles
 */
public interface PointTrackerTwoPass<T extends ImageBase> extends PointTracker<T> {

	/**
	 * Changes behavior of {@link PointTracker#process(boofcv.struct.image.ImageBase)} in that it will only
	 * update each track's location, but not its description, and the active list.  Call {@link #finishTracking()}
	 * to update the track's description, the inactive list, and the dropped list.  An exception is thrown if
	 * multiple calls to this function are made without calling {@link #finishTracking()}.
	 *
	 * @param image Next image in the sequence
	 */
	public void process(T image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs);

	/**
	 * Updates spacial information for each track and active list.  Does not change the track description or any other
	 * lists.  Can be called multiple times.
	 */
	public void performSecondPass(ImageMiscOps IMO);

	/**
	 * Finishes tracking and updates the track's description, updates inactive and drop track lists.
	 */
	public void finishTracking(ImageMiscOps IMO);

	/**
	 * Provides a hint for where the
	 * @param pixelX x-coordinate hint for where the track is in the image
	 * @param pixelY y-coordinate hint for where the track is in the image
	 * @param track The track for which the hint is being provided for
	 */
	public void setHint( double pixelX , double pixelY , PointTrack track );
}
