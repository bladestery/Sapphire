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

package boofcv.abst.sfm.d2;

import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.sfm.AccessPointTracks;
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
import boofcv.alg.sfm.d2.AssociatedPairTrack;
import boofcv.alg.sfm.d2.ImageMotionPtkSmartRespawn;
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
import georegression.struct.InvertibleTransform;
import georegression.struct.point.Point2D_F64;
import org.ddogleg.struct.GrowQueue_B;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around {@link boofcv.alg.sfm.d2.ImageMotionPtkSmartRespawn} for {@link ImageMotion2D}.
 *
 * @author Peter Abeles
 */
public class WrapImageMotionPtkSmartRespawn<T extends ImageBase, IT extends InvertibleTransform>
		implements ImageMotion2D<T,IT>, AccessPointTracks
{
	ImageMotionPtkSmartRespawn<T,IT> alg;
	boolean first = true;

	List<Point2D_F64> allTracks = new ArrayList<>();

	boolean inliersMarked = false;
	GrowQueue_B inliers = new GrowQueue_B(10);

	public WrapImageMotionPtkSmartRespawn(ImageMotionPtkSmartRespawn<T, IT> alg) {
		this.alg = alg;
	}

	@Override
	public boolean process(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI,
						   FactoryDistort FDs) {
		inliersMarked = false;

		boolean ret = alg.process(input, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
		if( first ) {
			alg.getMotion().changeKeyFrame(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
					GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
			alg.getMotion().resetTransforms();
			first = false;
			return true;
		}
		return ret;
	}

	@Override
	public void reset() {
		first = true;
		alg.getMotion().reset();
	}

	@Override
	public void setToFirst(InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI,
						   FactoryDistort FDs) {
		alg.getMotion().changeKeyFrame(ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);
		alg.getMotion().resetTransforms();
	}

	@Override
	public IT getFirstToCurrent() {
		return alg.getMotion().getWorldToCurr();
	}

	@Override
	public Class<IT> getTransformType() {
		return alg.getMotion().getModelType();
	}

	@Override
	public long getTrackId(int index) {
		return 0;
	}

	private void checkInitialize() {
		if( !inliersMarked ) {
			inliersMarked = true;

			List<PointTrack> active = alg.getMotion().getTracker().getActiveTracks(null);

			allTracks.clear();

			long tick = alg.getMotion().getTotalFramesProcessed();
			inliers.resize(active.size());

			for( int i = 0; i < active.size(); i++ ) {
				PointTrack t = active.get(i);
				AssociatedPairTrack info = t.getCookie();
				allTracks.add(t);
				// if it was used in the previous update then it is in the inlier set
				inliers.data[i] = info.lastUsed == tick;
			}
		}
	}

	@Override
	public List<Point2D_F64> getAllTracks() {
		checkInitialize();

		return allTracks;
	}

	@Override
	public boolean isInlier(int index) {
		checkInitialize();

		return inliers.data[ index ];
	}

	@Override
	public boolean isNew(int index) {
		return false;
	}
}
