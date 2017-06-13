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

import boofcv.abst.feature.associate.AssociateDescription2D;
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
import boofcv.struct.feature.AssociatedIndex;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;
import org.ddogleg.struct.FastQueue;

/**
 * Changes behavior of {@link DetectDescribeAssociate} so that it conforms to the {@link PointTrackerTwoPass} interface.
 * It can now take hints for where tracks might appear in the image.   If possible
 * {@link AssociateDescription2D#setSource(org.ddogleg.struct.FastQueue, org.ddogleg.struct.FastQueue)} will only be called once
 * on the second pass.
 *
 * @author Peter Abeles
 */
public class DetectDescribeAssociateTwoPass<I extends ImageGray, Desc extends TupleDesc>
	extends DetectDescribeAssociate<I,Desc> implements PointTrackerTwoPass<I>
{
	// associate used in the second pass
	AssociateDescription2D<Desc> associate2;
	// has source been set in associate for the second pass
	boolean sourceSet2;

	/**
	 * Configure the tracker.  The parameters associate and associate2 can be the same instance.
	 *
	 * @param manager Feature manager
	 * @param associate Association algorithm for the first pass
	 * @param associate2 Association algorithm for the second pass
	 * @param updateDescription Should descriptions be updated? Typically false
	 */
	public DetectDescribeAssociateTwoPass(DdaFeatureManager<I, Desc> manager,
										  AssociateDescription2D<Desc> associate,
										  AssociateDescription2D<Desc> associate2,
										  boolean updateDescription)
	{
		super(manager, associate, updateDescription);
		this.associate2 = associate2;
	}

	@Override
	public void process(I input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT, FactoryInterpolation FI, FactoryDistort FDs) {
		sourceSet2 = false;
		tracksActive.clear();
		tracksInactive.clear();
		tracksDropped.clear();
		tracksNew.clear();

		featDst.reset();
		locDst.reset();

		manager.detectFeatures(input, locDst, featDst, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT, FI, FDs);

		// skip if there are no features
		if( !tracksAll.isEmpty() ) {
			putIntoSrcList();

			associate.setSource(locSrc, featSrc);
			associate.setDestination(locDst, featDst);
			associate.associate();

			updateTrackLocation(associate.getMatches());
		}
	}

	@Override
	public void performSecondPass(ImageMiscOps IMO) {
		if( tracksAll.isEmpty() )
			return;

		// minimize the number of times set source is called.  In some implementations of associate this is an
		// expensive operation
		if( associate2 != associate && !sourceSet2 ) {
			sourceSet2 = true;
			associate.setSource(locSrc, featSrc);
		}
		associate2.setDestination(locDst, featDst);
		associate2.associate();

		updateTrackLocation(associate2.getMatches());
	}

	@Override
	public void finishTracking(ImageMiscOps IMO) {
		if( tracksAll.isEmpty() )
			return;

		// Update the track state using association information
		tracksActive.clear();
		updateTrackState(matches);

		// add unassociated to the list
		for( int i = 0; i < tracksAll.size(); i++ ) {
			if( !isAssociated[i] )
				tracksInactive.add(tracksAll.get(i));
		}
	}

	/**
	 * Update each track's location only and not its description.  Update the active list too
	 */
	protected void updateTrackLocation( FastQueue<AssociatedIndex> matches ) {
		tracksActive.clear();
		for( int i = 0; i < matches.size; i++ ) {
			AssociatedIndex indexes = matches.data[i];
			PointTrack track = tracksAll.get(indexes.src);
			Point2D_F64 loc = locDst.data[indexes.dst];
			track.set(loc.x, loc.y);
			tracksActive.add(track);
		}
		this.matches = matches;
	}

	@Override
	public void setHint( double pixelX , double pixelY , PointTrack track ) {
		track.x = pixelX;
		track.y = pixelY;
	}
}
