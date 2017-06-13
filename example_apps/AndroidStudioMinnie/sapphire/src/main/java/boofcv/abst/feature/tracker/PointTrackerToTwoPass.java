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

import java.util.List;

/**
 * Wrapper class that allows {@link PointTracker} to be used as a {@link PointTrackerTwoPass}.  Since
 * {@link PointTrack} does not have all the same capabilities of {@link PointTrackerTwoPass} not all operations
 * are supported or behave in exactly the same way. Use of this interface is only appropriate when
 * a two pass tracker is being used as a regular point tracker.
 *
 * @author Peter Abeles
 */
public class PointTrackerToTwoPass<T extends ImageBase>
		implements PointTrackerTwoPass<T>
{
	PointTracker<T> tracker;

	public PointTrackerToTwoPass(PointTracker<T> tracker) {
		this.tracker = tracker;
	}

	@Override
	public void process(T image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		tracker.process(image, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
	}

	@Override
	public void reset() {
		tracker.reset();
	}

	@Override
	public void dropAllTracks() {
		tracker.dropAllTracks();
	}

	@Override
	public boolean dropTrack(PointTrack track) {
		return tracker.dropTrack(track);
	}

	@Override
	public List<PointTrack> getAllTracks(List<PointTrack> list) {
		return tracker.getAllTracks(list);
	}

	@Override
	public List<PointTrack> getActiveTracks(List<PointTrack> list) {
		return tracker.getActiveTracks(list);
	}

	@Override
	public List<PointTrack> getInactiveTracks(List<PointTrack> list) {
		return tracker.getInactiveTracks(list);
	}

	@Override
	public List<PointTrack> getDroppedTracks(List<PointTrack> list) {
		return tracker.getDroppedTracks(list);
	}

	@Override
	public List<PointTrack> getNewTracks(List<PointTrack> list) {
		return tracker.getNewTracks(list);
	}

	@Override
	public void spawnTracks(InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
							GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
							ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
							GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
							FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		tracker.spawnTracks(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN, GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
	}

	@Override
	public void performSecondPass(ImageMiscOps IMO) {
		throw new RuntimeException("Operation not supported.  Need a real two pass tracker.");
	}

	@Override
	public void finishTracking(ImageMiscOps IMO) {
	}

	@Override
	public void setHint(double pixelX, double pixelY, PointTrack track) {
		throw new RuntimeException("Operation not supported.  Need a real two pass tracker.");
	}
}
