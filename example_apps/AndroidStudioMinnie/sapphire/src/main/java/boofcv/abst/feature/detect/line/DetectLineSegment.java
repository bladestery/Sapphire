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


import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.struct.image.ImageGray;
import georegression.struct.line.LineSegment2D_F32;

import java.util.List;

/**
 * <p>
 * Interface for detecting {@link LineSegment2D_F32 line segments} inside images.
 * </p>
 *
 * @author Peter Abeles
 */
public interface DetectLineSegment<T extends ImageGray> {

	/**
	 * Detect lines inside the image.
	 *
	 * @param input Input image.
	 * @return List of found line segments.
	 */
	public List<LineSegment2D_F32> detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
										  GradientToEdgeFeatures GTEF, ThresholdImageOps TIO, GeneralizedImageOps GIO, GGradientToEdgeFeatures GGTEF,
										  GThresholdImageOps GTIO, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO);
}
