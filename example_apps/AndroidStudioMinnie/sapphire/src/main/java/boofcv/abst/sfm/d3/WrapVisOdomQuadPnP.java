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

import boofcv.abst.sfm.AccessPointTracks3D;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.associate.AssociateStereo2D;
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
import boofcv.alg.geo.pose.PnPStereoDistanceReprojectionSq;
import boofcv.alg.geo.pose.RefinePnPStereo;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.sfm.d3.VisOdomQuadPnP;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.calib.CameraPinholeRadial;
import boofcv.struct.calib.StereoParameters;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.geo.Point2D3D;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.sfm.Stereo2D3D;
import georegression.struct.point.Point2D_F64;
import georegression.struct.point.Point3D_F64;
import georegression.struct.se.Se3_F64;
import org.ddogleg.fitting.modelset.ModelMatcher;
import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper around {@link VisOdomQuadPnP} for {@link StereoVisualOdometry}.
 *
 * @author Peter Abeles
 */
public class WrapVisOdomQuadPnP<T extends ImageGray,TD extends TupleDesc>
		implements StereoVisualOdometry<T>, AccessPointTracks3D
{
	VisOdomQuadPnP<T,TD> alg;
	RefinePnPStereo refine;
	AssociateStereo2D<TD> associateStereo;
	PnPStereoDistanceReprojectionSq distance;
	DistanceModelMonoPixels<Se3_F64,Point2D3D> distanceMono;
	Class<T> imageType;

	public WrapVisOdomQuadPnP(VisOdomQuadPnP<T, TD> alg,
							  RefinePnPStereo refine,
							  AssociateStereo2D<TD> associateStereo,
							  PnPStereoDistanceReprojectionSq distance,
							  DistanceModelMonoPixels<Se3_F64,Point2D3D> distanceMono,
							  Class<T> imageType)
	{
		this.alg = alg;
		this.refine = refine;
		this.associateStereo = associateStereo;
		this.distance = distance;
		this.distanceMono = distanceMono;
		this.imageType = imageType;
	}

	@Override
	public Point3D_F64 getTrackLocation(int index) {
		FastQueue<VisOdomQuadPnP.QuadView> features =  alg.getQuadViews();

		return features.get(index).X;
	}

	@Override
	public long getTrackId(int index) {
		return 0;
	}

	@Override
	public List<Point2D_F64> getAllTracks() {
		FastQueue<VisOdomQuadPnP.QuadView> features =  alg.getQuadViews();

		List<Point2D_F64> ret = new ArrayList<>();
		for( VisOdomQuadPnP.QuadView v : features.toList() )
			ret.add(v.v2); // new left camera

		return ret;
	}

	@Override
	public boolean isInlier(int index) {
		ModelMatcher<Se3_F64, Stereo2D3D> matcher = alg.getMatcher();
		int N = matcher.getMatchSet().size();
		for( int i = 0; i < N; i++ ) {
			if( matcher.getInputIndex(i) == index )
				return true;
		}
		return false;
	}

	@Override
	public boolean isNew(int index) {
		return false;// its always new
	}

	@Override
	public void setCalibration(StereoParameters parameters) {
		Se3_F64 leftToRight = parameters.getRightToLeft().invert(null);

		alg.setCalibration(parameters);
		associateStereo.setCalibration(parameters);
		distance.setStereoParameters(parameters);

		CameraPinholeRadial left = parameters.left;
		distanceMono.setIntrinsic(left.fx,left.fy,left.skew);

		if( refine != null )
			refine.setLeftToRight(leftToRight);
	}

	@Override
	public void reset() {
		alg.reset();
	}

	@Override
	public Se3_F64 getCameraToWorld() {
		return alg.getLeftToWorld();
	}

	@Override
	public boolean process(T leftImage, T rightImage, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT) {
		return alg.process(leftImage,rightImage);
	}

	@Override
	public boolean isFault() {
		return false;
	}

	@Override
	public ImageType<T> getImageType(ImageType IT) {
		return IT.single(imageType);
	}
}
