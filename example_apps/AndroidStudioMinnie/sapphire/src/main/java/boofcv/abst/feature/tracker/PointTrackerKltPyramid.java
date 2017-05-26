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
import boofcv.alg.tracker.klt.*;
import boofcv.alg.transform.pyramid.PyramidOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.pyramid.PyramidDiscrete;
import georegression.struct.point.Point2D_I16;
import sapphire.compiler.IMOGenerator;

import java.util.ArrayList;
import java.util.List;


/**
 * Wrapper around {@link boofcv.alg.tracker.klt.PyramidKltTracker} for {@link PointTracker}.  Every track
 * will have the same size and shaped descriptor.  If any fault is encountered the track will be dropped.
 *
 * @author Peter Abeles
 */
public class PointTrackerKltPyramid<I extends ImageGray,D extends ImageGray>
		implements PointTracker<I>
{
	// reference to input image
	protected I input;

	// Updates the image pyramid's gradient.
	protected ImageGradient<I,D> gradient;

	// storage for image pyramid
	protected PyramidDiscrete<I> basePyramid;
	protected D[] derivX;
	protected D[] derivY;
	protected Class<D> derivType;

	// configuration for the KLT tracker
	protected KltConfig config;
	// size of the template/feature description
	protected int templateRadius;

	// list of features which are actively being tracked
	protected List<PyramidKltFeature> active = new ArrayList<>();
	// list of features which were just spawned
	protected List<PyramidKltFeature> spawned = new ArrayList<>();
	// list of features which were just dropped
	protected List<PyramidKltFeature> dropped = new ArrayList<>();
	// feature data available for future tracking
	protected List<PyramidKltFeature> unused = new ArrayList<>();

	// the tracker
	protected PyramidKltTracker<I, D> tracker;

	// selects point features
	private GeneralFeatureDetector<I, D> detector;
	// list of corners which should be ignored by the corner detector
	private QueueCorner excludeList = new QueueCorner(10);

	// number of features tracked so far
	private long totalFeatures = 0;

	/**
	 * Constructor which specified the KLT track manager and how the image pyramids are computed.
	 *
	 * @param config KLT tracker configuration
	 * @param templateRadius Radius of square templates that are tracked
	 * @param pyramid The image pyramid which KLT is tracking inside of
	 * @param detector Feature detector.   If null then no feature detector will be available and spawn won't work.
	 * @param gradient Computes gradient image pyramid.
	 * @param interpInput Interpolation used on input image
	 * @param interpDeriv Interpolation used on gradient images
	 * @param derivType Type of image the gradient is
	 */
	public PointTrackerKltPyramid(KltConfig config,
								  int templateRadius ,
								  PyramidDiscrete<I> pyramid,
								  GeneralFeatureDetector<I, D> detector,
								  ImageGradient<I, D> gradient,
								  InterpolateRectangle<I> interpInput,
								  InterpolateRectangle<D> interpDeriv,
								  Class<D> derivType ) {

		this.config = config;
		this.templateRadius = templateRadius;
		this.gradient = gradient;
		this.basePyramid = pyramid;
		this.derivType = derivType;

		KltTracker<I, D> klt = new KltTracker<>(interpInput, interpDeriv, config);
		tracker = new PyramidKltTracker<>(klt);

		if( detector != null) {
			if (detector.getRequiresHessian())
				throw new IllegalArgumentException("Hessian based feature detectors not yet supported");

			this.detector = detector;
		}
	}

	private void addTrackToUnused() {
		int numLayers = basePyramid.getNumLayers();
		PyramidKltFeature t = new PyramidKltFeature(numLayers, templateRadius);

		PointTrack p = new PointTrack();
		p.setDescription(t);
		t.cookie = p;

		unused.add(t);
	}

	/**
	 * Creates a new feature track at the specified location. Must only be called after
	 * {@link #process(ImageGray)} has been called.  It can fail if there
	 * is insufficient texture
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return the new track if successful or null if no new track could be created
	 */
	public PointTrack addTrack(double x , double y, ImageMiscOps IMO) {
		if( !input.isInBounds((int)x,(int)y))
			return null;

		// grow the number of tracks if needed
		if( unused.isEmpty() )
			addTrackToUnused();

		// TODO make sure the feature is inside the image

		PyramidKltFeature t = unused.remove(unused.size() - 1);
		t.setPosition((float)x,(float)y);
		tracker.setDescription(t, IMO);

		PointTrack p = (PointTrack)t.cookie;
		p.set(x,y);

		if( checkValidSpawn(p) ) {
			active.add(t);
			return p;
		}

		return null;
	}

	@Override
	public void spawnTracks(InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
							GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
							ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
							GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
							FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT) {
		spawned.clear();

		// used to convert it from the scale of the bottom layer into the original image
		float scaleBottom = (float) basePyramid.getScale(0);

		// exclude active tracks
		excludeList.reset();
		for (int i = 0; i < active.size(); i++) {
			PyramidKltFeature f = active.get(i);
			excludeList.add((int) (f.x / scaleBottom), (int) (f.y / scaleBottom));
		}

		// find new tracks, but no more than the max
		detector.setExcludeMaximum(excludeList);
		detector.process(basePyramid.getLayer(0), derivX[0], derivY[0], null, null, null, GIMO, IMO, ISC, CNN, CINB, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, CJBG, CI, UW, IT);

		// extract the features
		QueueCorner found = detector.getMaximums();

		// grow the number of tracks if needed
		while( unused.size() < found.size() )
			addTrackToUnused();

		for (int i = 0; i < found.size() && !unused.isEmpty(); i++) {
			Point2D_I16 pt = found.get(i);

			// set up pyramid description
			PyramidKltFeature t = unused.remove(unused.size() - 1);
			t.x = pt.x * scaleBottom;
			t.y = pt.y * scaleBottom;

			tracker.setDescription(t, IMO);

			// set up point description
			PointTrack p = t.getCookie();
			p.set(t.x,t.y);

			if( checkValidSpawn(p) ) {
				p.featureId = totalFeatures++;

				// add to appropriate lists
				active.add(t);
				spawned.add(t);
			} else {
				unused.add(t);
			}
		}
	}

	/**
	 * Returns true if a new track can be spawned here.  Intended to be overloaded
	 */
	protected boolean checkValidSpawn( PointTrack p ) {
		return true;
	}

	@Override
	public void dropAllTracks() {
		unused.addAll(active);
		active.clear();
		dropped.clear();
	}

	@Override
	public void process(I image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT) {
		this.input = image;

		spawned.clear();
		dropped.clear();

		// update image pyramids
		basePyramid.process(image, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW, IT);
		declareOutput(GIO);
		PyramidOps.gradient(basePyramid, gradient, derivX,derivY, ISC, DHF, CINB, CJBG, GSO, GSUO);

		// track features
		tracker.setImage(basePyramid,derivX,derivY);
		for( int i = 0; i < active.size(); ) {
			PyramidKltFeature t = active.get(i);
			KltTrackFault ret = tracker.track(t, IMO);

			boolean success = false;

			if( ret == KltTrackFault.SUCCESS ) {
				// discard a track if its center drifts outside the image.
				if( image.isInBounds((int)t.x,(int)t.y) && tracker.setDescription(t, IMO) ) {
					PointTrack p = t.getCookie();
					p.set(t.x,t.y);
					i++;
					success = true;
				}
			}

			if( !success ) {
				active.remove(i);
				dropped.add( t );
				unused.add( t );
			}
		}
	}

	protected void declareOutput(GeneralizedImageOps GIO) {
		if( derivX == null ) {
			// declare storage for image derivative since the image size is now known
			derivX = PyramidOps.declareOutput(basePyramid, derivType, GIO);
			derivY = PyramidOps.declareOutput(basePyramid,derivType, GIO);
		}
		else if( derivX[0].width != basePyramid.getLayer(0).width ||
				derivX[0].height != basePyramid.getLayer(0).height )
		{
			PyramidOps.reshapeOutput(basePyramid,derivX);
			PyramidOps.reshapeOutput(basePyramid,derivY);
		}
	}

	@Override
	public boolean dropTrack(PointTrack track) {
		if( active.remove((PyramidKltFeature)track.getDescription()) ) {
			// only recycle the description if it is in the active list.  This avoids the problem of adding the
			// same description multiple times
			unused.add((PyramidKltFeature)track.getDescription());
			return true;
		}
		return false;
	}

	@Override
	public List<PointTrack> getActiveTracks( List<PointTrack> list ) {
		if( list == null )
			list = new ArrayList<>();

		addToList(active,list);

		return list;
	}

	/**
	 * KLT does not have inactive tracks since all tracks are dropped if a problem occurs.
	 */
	@Override
	public List<PointTrack> getInactiveTracks(List<PointTrack> list) {
		if( list == null )
			list = new ArrayList<>();

		return list;
	}

	@Override
	public List<PointTrack> getDroppedTracks( List<PointTrack> list ) {
		if( list == null )
			list = new ArrayList<>();

		addToList(dropped,list);

		return list;
	}

	@Override
	public List<PointTrack> getNewTracks( List<PointTrack> list ) {
		if( list == null )
			list = new ArrayList<>();

		addToList(spawned,list);

		return list;
	}

	@Override
	public List<PointTrack> getAllTracks( List<PointTrack> list ) {
		return getActiveTracks(list);
	}

	protected void addToList( List<PyramidKltFeature> in , List<PointTrack> out ) {
		for( PyramidKltFeature t : in ) {
			out.add( (PointTrack)t.cookie );
		}
	}

	@Override
	public void reset() {
		dropAllTracks();
		totalFeatures = 0;
	}
}
