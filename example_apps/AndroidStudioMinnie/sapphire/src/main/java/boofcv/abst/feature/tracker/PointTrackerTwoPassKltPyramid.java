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

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.feature.detect.interest.GeneralFeatureDetector;
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
import boofcv.alg.interpolate.InterpolateRectangle;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.tracker.klt.KltConfig;
import boofcv.alg.tracker.klt.KltTrackFault;
import boofcv.alg.tracker.klt.PyramidKltFeature;
import boofcv.alg.transform.pyramid.PyramidOps;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.misc.BoofMiscOps;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.pyramid.PyramidDiscrete;

import java.util.ArrayList;
import java.util.List;

/**
 * Changes behavior of {@link PointTrackerKltPyramid} so that it conforms to the {@link PointTrackerTwoPass} interface.
 *
 * @author Peter Abeles
 */
public class PointTrackerTwoPassKltPyramid<I extends ImageGray,D extends ImageGray>
	extends PointTrackerKltPyramid<I,D> implements PointTrackerTwoPass<I>
{
	private static InputSanityCheck ISC;
	private static DerivativeHelperFunctions DHF;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveJustBorder_General CJBG;
	private static GradientSobel_Outer GSO;
	private static GradientSobel_UnrolledOuter GSUO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ConvolveNormalized CN;
	private static GBlurImageOps GBIO;
	private static GeneralizedImageOps GIO;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static FactoryImageBorderAlgs FIBA;
	private static ImageBorderValue IBV;
	private static FastHessianFeatureDetector FHFD;
	private static FactoryImageBorder FIB;
	private static ImageType IT;
	private static FactoryBlurFilter FBF;
	// list of active tracks before the current image is processed
	List<PyramidKltFeature> originalActive = new ArrayList<>();

	// list of tracks that were dropped, but won't really be dropped until tracking finishes
	List<PyramidKltFeature> candidateDrop = new ArrayList<>();

	// has finished tracking been called
	boolean finishedTracking;

	public PointTrackerTwoPassKltPyramid(KltConfig config,
										 int templateRadius ,
										 PyramidDiscrete<I> pyramid,
										 GeneralFeatureDetector<I, D> detector,
										 ImageGradient<I, D> gradient,
										 InterpolateRectangle<I> interpInput,
										 InterpolateRectangle<D> interpDeriv)
	{
		super(config, templateRadius, pyramid , detector, gradient, interpInput, interpDeriv,
				gradient.getDerivativeType(IT).getImageClass());
	}

	@Override
	public void process(I image) {
		this.input = image;

		finishedTracking = false;
		spawned.clear();
		dropped.clear();

		// update image pyramids
		basePyramid.process(image, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG);
		declareOutput();
		PyramidOps.gradient(basePyramid, gradient, derivX, derivY);

		// setup active list
		originalActive.clear();
		originalActive.addAll( active );

		// track features
		candidateDrop.clear();
		active.clear();

		tracker.setImage(basePyramid,derivX,derivY);
		for( int i = 0; i < originalActive.size(); i++ ) {
			PyramidKltFeature t = originalActive.get(i);
			KltTrackFault ret = tracker.track(t);

			boolean success = false;

			if( ret == KltTrackFault.SUCCESS ) {
				// discard a track if its center drifts outside the image.
				if( BoofMiscOps.checkInside(input, t.x, t.y)) {
					active.add(t);
					PointTrack p = t.getCookie();
					p.set(t.x,t.y);
					success = true;
				}
			}

			if( !success ) {
				candidateDrop.add(t);
			}
		}
	}

	@Override
	public void performSecondPass() {
		candidateDrop.clear();
		active.clear();

		for( int i = 0; i < originalActive.size(); i++ ) {
			PyramidKltFeature t = originalActive.get(i);
			KltTrackFault ret = tracker.track(t);

			boolean success = false;

			if( ret == KltTrackFault.SUCCESS ) {
				// discard a track if its center drifts outside the image.
				if(BoofMiscOps.checkInside(input, t.x, t.y)) {
					active.add(t);
					PointTrack p = t.getCookie();
					p.set(t.x,t.y);
					success = true;
				}
			}
			if( !success) {
				candidateDrop.add(t);
			}
		}
	}

	@Override
	public void finishTracking() {
		for( int i = 0; i < active.size(); ) {
			PyramidKltFeature t = active.get(i);
			if( tracker.setDescription(t) ) {
				i++;
			} else {
				candidateDrop.add(t);
				active.remove(i);
			}
		}

		for( int i = 0; i < candidateDrop.size(); i++ ) {
			PyramidKltFeature t = candidateDrop.get(i);
			dropped.add( t );
			unused.add( t );
		}

		finishedTracking = true;
	}

	@Override
	public void setHint(double pixelX, double pixelY, PointTrack track) {
		PyramidKltFeature kltTrack = track.getDescription();
		kltTrack.setPosition((float)pixelX,(float)pixelY);
	}

	@Override
	public List<PointTrack> getAllTracks( List<PointTrack> list ) {
		if( list == null )
			list = new ArrayList<>();

		if( finishedTracking )
			addToList(active,list);
		else
			addToList(originalActive,list);

		return list;
	}
}
