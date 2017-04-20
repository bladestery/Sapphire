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

package boofcv.alg.feature.detect.edge;

import boofcv.abst.filter.blur.BlurFilter;
import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppression;
import boofcv.alg.filter.blur.BlurImageOps;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.alg.filter.blur.impl.ImplMedianHistogramInner;
import boofcv.alg.filter.blur.impl.ImplMedianSortEdgeNaive;
import boofcv.alg.filter.blur.impl.ImplMedianSortNaive;
import boofcv.alg.filter.convolve.ConvolveImageMean;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS8;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_I32;

import java.util.List;

import sapphire.app.SapphireObject;

/**
 * Implementation of canny edge detector.  The canny edge detector detects the edges of objects
 * using a hysteresis threshold.  When scanning the image pixels with edge intensities below
 * the high threshold are ignored.  After a pixel is found that exceeds the high threshold any
 * pixel that is connect to it directly or indirectly just needs to exceed the low threshold.
 *
 * The output from this class can be configured to output a binary edge image and/or a set of contours
 * for each point in the contour image.
 *
 * @author Peter Abeles
 */
public class CannyEdge<T extends ImageGray, D extends ImageGray> implements SapphireObject {
	// blurs the input image
	private BlurFilter<T> blur;

	// computes the image gradient
	private ImageGradient<T,D> gradient;

	// blurred input image
	private T blurred;

	// image gradient
	private D derivX;
	private D derivY;

	// edge intensity
	private GrayF32 intensity = new GrayF32(1,1);
	protected GrayF32 suppressed = new GrayF32(1,1);
	// edge direction in radians
	private GrayF32 angle = new GrayF32(1,1);
	// quantized direction
	private GrayS8 direction = new GrayS8(1,1);
	// work space
	private GrayU8 work = new GrayU8(1,1);

	// different algorithms for performing hysteresis thresholding
	protected HysteresisEdgeTracePoints hysteresisPts; // saves a list of points
	protected HysteresisEdgeTraceMark hysteresisMark; // just marks a binary image

	/**
	 * Specify internal algorithms and behavior.
	 *
	 * @param blur Initial blur applied to image.
	 * @param gradient Computes the image gradient.
	 * @param saveTrace Should it save a list of points that compose the objects contour/trace?
	 */
	public CannyEdge(BlurFilter<T> blur, ImageGradient<T, D> gradient, boolean saveTrace, GeneralizedImageOps GIO, ImageType IT) {
		this.blur = blur;
		this.gradient = gradient;

		Class<T> imageType = blur.getInputType(IT).getImageClass();

		blurred = GIO.createSingleBand(imageType, 1, 1);
		derivX = gradient.getDerivativeType(IT).createImage(1,1);
		derivY = gradient.getDerivativeType(IT).createImage(1, 1);

		if( saveTrace ) {
			hysteresisPts = new HysteresisEdgeTracePoints();
		} else {
			hysteresisMark = new HysteresisEdgeTraceMark();
		}
	}

	/**
	 * <p>
	 * Runs a canny edge detector on the input image given the provided thresholds.  If configured to save
	 * a list of trace points then the output image is optional.
	 * </p>
	 * <p>
	 * NOTE: Input and output can be the same instance, if the image type allows it.
	 * </p>
	 * @param input Input image. Not modified.
	 * @param threshLow Lower threshold. &ge; 0.
	 * @param threshHigh Upper threshold. &ge; 0.
	 * @param output (Might be option) Output binary image.  Edge pixels are marked with 1 and everything else 0.
	 */
	public void process(T input , float threshLow, float threshHigh , GrayU8 output, ImageMiscOps IMO, GBlurImageOps GBIO,
						ImageBorderValue IBV, GradientToEdgeFeatures GTEF, ImplEdgeNonMaxSuppression IENMS, FactoryImageBorderAlgs FIBA,
						GGradientToEdgeFeatures GGTEF, ImageStatistics IS, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
						ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						DerivativeHelperFunctions DHF) {

		if( threshLow < 0 || threshHigh < 0 )
			throw new IllegalArgumentException("Threshold must be >= zero!");

		if( hysteresisMark != null ) {
			if( output == null )
				throw new IllegalArgumentException("An output image must be specified when configured to mark edge points");
		}

		// setup internal data structures
		blurred.reshape(input.width,input.height);
		derivX.reshape(input.width,input.height);
		derivY.reshape(input.width,input.height);
		intensity.reshape(input.width,input.height);
		suppressed.reshape(input.width,input.height);
		angle.reshape(input.width,input.height);
		direction.reshape(input.width,input.height);
		work.reshape(input.width,input.height);

		// run canny edge detector
		blur.process(input,blurred, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM);
		gradient.process(blurred, derivX, derivY, ISC, DHF, CINB);
		GGTEF.intensityAbs(derivX, derivY, intensity, GTEF, ISC);
		GGTEF.direction(derivX, derivY, angle, GTEF, ISC);
		GTEF.discretizeDirection4(angle, direction, ISC, GIO);
		GTEF.nonMaxSuppression4(intensity, direction, suppressed, IBV, IENMS, FIBA, ISC);

		performThresholding(threshLow, threshHigh, output, IMO, IS, ISC);
	}

	protected void performThresholding(float threshLow, float threshHigh, GrayU8 output, ImageMiscOps IMO, ImageStatistics IS, InputSanityCheck ISC) {
		if( hysteresisPts != null ) {
			hysteresisPts.process(suppressed,direction,threshLow,threshHigh, ISC);

			// if there is an output image write the contour to it
			if( output != null ) {
				IMO.fill(output, 0);
				for( EdgeContour e : hysteresisPts.getContours() ) {
					for( EdgeSegment s : e.segments)
						for( Point2D_I32 p : s.points )
							output.unsafe_set(p.x,p.y,1);
				}
			}
		} else {
			hysteresisMark.process(suppressed,direction,threshLow,threshHigh,output, ISC);
		}
	}

	public List<EdgeContour> getContours() {
		return hysteresisPts.getContours();
	}
}
