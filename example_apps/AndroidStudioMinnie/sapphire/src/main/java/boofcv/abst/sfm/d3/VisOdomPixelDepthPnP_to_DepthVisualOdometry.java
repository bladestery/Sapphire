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

package boofcv.abst.sfm.d3;

import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.sfm.AccessPointTracks3D;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.distort.LensDistortionOps;
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
import boofcv.alg.geo.DistanceModelMonoPixels;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.sfm.DepthSparse3D;
import boofcv.alg.sfm.d3.VisOdomPixelDepthPnP;
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
import boofcv.struct.calib.CameraPinholeRadial;
import boofcv.struct.distort.PixelTransform2_F32;
import boofcv.struct.distort.Point2Transform2_F64;
import boofcv.struct.geo.Point2D3D;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.sfm.Point2D3DTrack;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.se.Se3_F64;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around {@link VisOdomPixelDepthPnP} for {@link DepthVisualOdometry}.
 *
 * @author Peter Abeles
 */
// TODO WARNING! active list has been modified by dropping and adding tracks
// this is probably true of other SFM algorithms
public class VisOdomPixelDepthPnP_to_DepthVisualOdometry<Vis extends ImageBase, Depth extends ImageGray>
	implements DepthVisualOdometry<Vis,Depth> , AccessPointTracks3D
{
	// low level algorithm
	DepthSparse3D<Depth> sparse3D;
	VisOdomPixelDepthPnP<Vis> alg;
	DistanceModelMonoPixels<Se3_F64,Point2D3D> distance;
	ImageType<Vis> visualType;
	Class<Depth> depthType;
	boolean success;

	List<PointTrack> active = new ArrayList<>();

	public VisOdomPixelDepthPnP_to_DepthVisualOdometry(DepthSparse3D<Depth> sparse3D, VisOdomPixelDepthPnP<Vis> alg,
													   DistanceModelMonoPixels<Se3_F64, Point2D3D> distance,
													   ImageType<Vis> visualType, Class<Depth> depthType) {
		this.sparse3D = sparse3D;
		this.alg = alg;
		this.distance = distance;
		this.visualType = visualType;
		this.depthType = depthType;
	}

	@Override
	public Point3D_F64 getTrackLocation(int index) {
		// TODO see comment above
		try {
			PointTrack t = alg.getTracker().getActiveTracks(null).get(index);
			return ((Point2D3D)t.getCookie()).getLocation();
		} catch( IndexOutOfBoundsException e ) {
			return new Point3D_F64();
		}
	}

	@Override
	public long getTrackId(int index) {
		return active.get(index).featureId;
	}

	@Override
	public List<Point2D_F64> getAllTracks() {
		return (List)active;
	}

	@Override
	public boolean isInlier(int index) {
		Point2D3DTrack t = active.get(index).getCookie();
		return t.lastInlier == alg.getTick();
	}

	@Override
	public boolean isNew(int index) {
		PointTrack t = alg.getTracker().getActiveTracks(null).get(index);
		return alg.getTracker().getNewTracks(null).contains(t);
	}

	@Override
	public void setCalibration(CameraPinholeRadial paramVisual, PixelTransform2_F32 visToDepth, LensDistortionOps LDO) {
		sparse3D.configure(paramVisual,visToDepth);

		Point2Transform2_F64 leftPixelToNorm = LDO.transformPoint(paramVisual).undistort_F64(true,false);
		Point2Transform2_F64 leftNormToPixel = LDO.transformPoint(paramVisual).distort_F64(false,true);

		alg.setPixelToNorm(leftPixelToNorm);
		alg.setNormToPixel(leftNormToPixel);

		distance.setIntrinsic(paramVisual.fx,paramVisual.fy,paramVisual.skew);
	}

	@Override
	public boolean process(Vis visual, Depth depth, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		sparse3D.setDepthImage(depth);
		success = alg.process(visual, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);

		active.clear();
		alg.getTracker().getActiveTracks(active);

		return success;
	}

	@Override
	public void reset() {
		alg.reset();
	}

	@Override
	public boolean isFault() {
		return !success;
	}

	@Override
	public Se3_F64 getCameraToWorld() {
		return alg.getCurrToWorld();
	}

	@Override
	public ImageType<Vis> getVisualType() {
		return visualType;
	}

	@Override
	public Class<Depth> getDepthType() {
		return depthType;
	}
}
