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

package boofcv.abst.feature.detect.line;


import android.renderscript.ScriptGroup;

import boofcv.abst.feature.detect.extract.ConfigExtract;
import boofcv.abst.feature.detect.extract.NonMaxSuppression;
import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.impl.ImplEdgeNonMaxSuppressionCrude;
import boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm;
import boofcv.alg.feature.detect.line.ImageLinePruneMerge;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.struct.line.LineParametric2D_F32;
import sapphire.app.SapphireObject;

import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Full processing chain for detecting lines using a foot of norm parametrization inside
 * a Hough transform.
 * </p>
 *
 * <p>
 * USAGE NOTES: Blurring the image prior to processing can often improve performance.
 * Results will not be perfect and to detect all the obvious lines in the image several false
 * positives might be returned.
 * </p>
 *
 * @see boofcv.alg.feature.detect.line.HoughTransformLineFootOfNorm
 *
 * @author Peter Abeles
 */
public class DetectLineHoughFoot <I extends ImageGray, D extends ImageGray> implements DetectLine<I>, SapphireObject {
	// transform algorithm
	HoughTransformLineFootOfNorm alg;

	// computes image gradient
	ImageGradient<I,D> gradient;

	// used to create binary edge image
	float thresholdEdge;

	// image gradient
	D derivX;
	D derivY;

	// edge intensity image
	GrayF32 intensity = new GrayF32(1,1);

	// detected edge image
	GrayU8 binary = new GrayU8(1,1);

	// the maximum number of lines it will return
	int maxLines;

	// post processing pruning
	ImageLinePruneMerge post = new ImageLinePruneMerge();

	/**
	 * Specifies detection parameters.  The suggested parameters should be used as a starting point and will
	 * likely need to be tuned significantly for each different scene.
	 *
	 * @param localMaxRadius Lines in transform space must be a local max in a region with this radius. Try 5;
	 * @param minCounts Minimum number of counts/votes inside the transformed image. Try 5.
	 * @param minDistanceFromOrigin Lines which are this close to the origin of the transformed image are ignored.  Try 5.
	 * @param thresholdEdge Threshold for classifying pixels as edge or not.  Try 30.
	 * @param gradient Computes the image gradient.
	 */
	public DetectLineHoughFoot( int localMaxRadius,
								int minCounts ,
								int minDistanceFromOrigin ,
								float thresholdEdge ,
								int maxLines ,
								ImageGradient<I,D> gradient ,
								ImageType IT, FactoryFeatureExtractor FFE)
	{
		this.gradient = gradient;
		this.thresholdEdge = thresholdEdge;
		this.maxLines = maxLines;
		NonMaxSuppression extractor = FFE.nonmaxCandidate(
				new ConfigExtract(localMaxRadius, minCounts, 0, false));
		alg = new HoughTransformLineFootOfNorm(extractor,minDistanceFromOrigin);
		derivX = gradient.getDerivativeType(IT).createImage(1,1);
		derivY = gradient.getDerivativeType(IT).createImage(1, 1);
	}

	@Override
	public List<LineParametric2D_F32> detect(I input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
											 GradientToEdgeFeatures GTEF, GeneralizedImageOps GIO, ThresholdImageOps TIO, GGradientToEdgeFeatures GGTEF, ConvolveJustBorder_General CJBG,
											 GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, ImageMiscOps IMO, ImplEdgeNonMaxSuppressionCrude IENMSC, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV) {
		derivX.reshape(input.width,input.height);
		derivY.reshape(input.width,input.height);
		intensity.reshape(input.width,input.height);
		binary.reshape(input.width,input.height);
		gradient.process(input,derivX,derivY, ISC, DHF, CINB, CJBG, GSO, GSUO);
		GGTEF.intensityAbs(derivX, derivY, intensity, GTEF, ISC);

		TIO.threshold(intensity, binary, thresholdEdge, false, ISC, GIO);

		alg.transform(derivX,derivY,binary, ISC, IMO);
		FastQueue<LineParametric2D_F32> lines = alg.extractLines();

		List<LineParametric2D_F32> ret = new ArrayList<>();
		for( int i = 0; i < lines.size; i++ )
			ret.add(lines.get(i));

		ret = pruneLines(input,ret);

		return ret;
	}

	private List<LineParametric2D_F32> pruneLines(I input, List<LineParametric2D_F32> ret) {
		float intensity[] = alg.getFoundIntensity();
		post.reset();
		for( int i = 0; i < ret.size(); i++ ) {
			post.add(ret.get(i),intensity[i]);
		}

		// NOTE: angular accuracy is a function of range from sub image center.  This pruning
		// function uses a constant value for range accuracy.  A custom algorithm should really
		// be used here.
		// NOTE: Thresholds should not be hardcoded...
		post.pruneSimilar((float) (Math.PI * 0.04), 10, input.width, input.height);
		post.pruneNBest(maxLines);

		return post.createList();
	}

	public HoughTransformLineFootOfNorm getTransform() {
		return alg;
	}

	public D getDerivX() {
		return derivX;
	}

	public D getDerivY() {
		return derivY;
	}

	public GrayF32 getEdgeIntensity() {
		return intensity;
	}

	public GrayU8 getBinary() {
		return binary;
	}
}
