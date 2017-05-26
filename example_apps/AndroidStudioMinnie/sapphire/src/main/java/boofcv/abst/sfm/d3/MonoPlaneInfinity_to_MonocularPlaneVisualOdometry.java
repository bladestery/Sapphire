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

package boofcv.abst.sfm.d3;

import boofcv.abst.feature.tracker.PointTrack;
import boofcv.abst.sfm.AccessPointTracks3D;
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
import boofcv.alg.sfm.d3.VisOdomMonoPlaneInfinity;
import boofcv.alg.sfm.robust.DistancePlane2DToPixelSq;
import boofcv.alg.sfm.robust.GenerateSe2_PlanePtPixel;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.calib.MonoPlaneParameters;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.se.Se3_F64;

import java.util.List;

/**
 * Wrapper around {@link VisOdomMonoPlaneInfinity} for {@link MonocularPlaneVisualOdometry}.
 *
 * @author Peter Abeles
 */
public class MonoPlaneInfinity_to_MonocularPlaneVisualOdometry<T extends ImageBase>
		implements MonocularPlaneVisualOdometry<T> , AccessPointTracks3D
{
	VisOdomMonoPlaneInfinity<T> alg;
	DistancePlane2DToPixelSq distance;
	GenerateSe2_PlanePtPixel generator;

	ImageType<T> imageType;

	boolean fault;
	Se3_F64 cameraToWorld = new Se3_F64();

	// list of active tracks
	List<PointTrack> active = null;

	Point3D_F64 point3D = new Point3D_F64();

	public MonoPlaneInfinity_to_MonocularPlaneVisualOdometry(VisOdomMonoPlaneInfinity<T> alg,
															 DistancePlane2DToPixelSq distance,
															 GenerateSe2_PlanePtPixel generator,
															 ImageType<T> imageType) {
		this.alg = alg;
		this.distance = distance;
		this.generator = generator;
		this.imageType = imageType;
	}

	@Override
	public void setCalibration( MonoPlaneParameters param ) {
		alg.setIntrinsic(param.intrinsic);
		distance.setIntrinsic(param.intrinsic.fx,param.intrinsic.fy,param.intrinsic.skew);

		alg.setExtrinsic(param.planeToCamera);
		generator.setExtrinsic(param.planeToCamera);
		distance.setExtrinsic(param.planeToCamera);
	}

	@Override
	public boolean process(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT) {

		active = null;
		fault = alg.process(input, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT);

		return fault;
	}

	@Override
	public ImageType<T> getImageType() {
		return imageType;
	}

	@Override
	public void reset() {
		active = null;
		alg.reset();
		cameraToWorld.reset();
	}

	@Override
	public boolean isFault() {
		return fault;
	}

	@Override
	public Se3_F64 getCameraToWorld() {
		Se3_F64 worldToCamera = alg.getWorldToCurr3D();
		worldToCamera.invert(cameraToWorld);

		return cameraToWorld;
	}

	@Override
	public Point3D_F64 getTrackLocation(int index) {
		if( active == null )
			active = alg.getTracker().getActiveTracks(null);

		active.get(index);

		VisOdomMonoPlaneInfinity.VoTrack track = active.get(index).getCookie();

		if( track.onPlane ) {
			point3D.x = -track.ground.y;
			point3D.z = track.ground.x;
			point3D.y = 0;

		} else {
			// just put it some place far away
			point3D.x = -track.ground.y*1000;
			point3D.z = track.ground.x*1000;
			point3D.y = 0;
		}

//		SePointOps_F64.transform(cameraToWorld,point3D,point3D);

		return point3D;
	}

	@Override
	public long getTrackId(int index) {
		if( active == null )
			active = alg.getTracker().getActiveTracks(null);

		PointTrack t = active.get(index);
		return t.featureId;
	}

	@Override
	public List<Point2D_F64> getAllTracks() {
		if( active == null )
			active = alg.getTracker().getActiveTracks(null);

		return (List)active;
	}

	@Override
	public boolean isInlier(int index) {
		if( active == null )
			active = alg.getTracker().getActiveTracks(null);

		PointTrack t = active.get(index);
		VisOdomMonoPlaneInfinity.VoTrack v = t.getCookie();

		return v.lastInlier == alg.getTick();
	}

	@Override
	public boolean isNew(int index) {
		// need to figure out a way to efficiently implement this
		return false;
	}

}
