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


import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.edge.GGradientToEdgeFeatures;
import boofcv.alg.feature.detect.edge.GradientToEdgeFeatures;
import boofcv.alg.feature.detect.line.ConnectLinesGrid;
import boofcv.alg.feature.detect.line.GridRansacLineDetector;
import boofcv.alg.feature.detect.line.LineImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.struct.feature.MatrixOfList;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import georegression.struct.line.LineSegment2D_F32;
import sapphire.app.SapphireObject;
import sapphire.compiler.GTEFGenerator;

import java.util.List;

/**
 * @author Peter Abeles
 */
// TODO update description in FactoryDetectLineAlgs
public class DetectLineSegmentsGridRansac<T extends ImageGray, D extends ImageGray>
		implements DetectLineSegment<T>, SapphireObject
{
	GridRansacLineDetector<D> detectorGrid;
	ConnectLinesGrid connect;

	D derivX;
	D derivY;
	GrayF32 edgeIntensity;
	GrayU8 detected;

	ImageGradient<T,D> gradient;

	double edgeThreshold;

	public DetectLineSegmentsGridRansac(GridRansacLineDetector<D> detectorGrid,
										ConnectLinesGrid connect,
										ImageGradient<T,D> gradient,
										double edgeThreshold ,
										Class<T> imageType , Class<D> derivType,
										GeneralizedImageOps GIO) {
		this.detectorGrid = detectorGrid;
		this.connect = connect;
		this.gradient = gradient;
		this.edgeThreshold = edgeThreshold;

		derivX = GIO.createSingleBand(derivType, 1, 1);
		derivY = GIO.createSingleBand(derivType, 1, 1);
		edgeIntensity = new GrayF32(1,1);
		detected = new GrayU8(1,1);
	}

	@Override
	public List<LineSegment2D_F32> detect(T input, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
										  GradientToEdgeFeatures GTEF, ThresholdImageOps TIO, GeneralizedImageOps GIO, GGradientToEdgeFeatures GGTEF,
										  GThresholdImageOps GTIO, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO) {

		derivX.reshape(input.width,input.height);
		derivY.reshape(input.width,input.height);
		edgeIntensity.reshape(input.width,input.height);
		detected.reshape(input.width,input.height);

		gradient.process(input,derivX,derivY, ISC, DHF, CINB, CJBG, GSO, GSUO);
		GGTEF.intensityAbs(derivX, derivY, edgeIntensity, GTEF, ISC);
		GTIO.threshold(edgeIntensity, detected, edgeThreshold, false, TIO, ISC, GIO);

		detectorGrid.process(derivX,derivY,detected, ISC);

		MatrixOfList<LineSegment2D_F32> grid = detectorGrid.getFoundLines();
		if( connect != null ) {
			connect.process(grid);
		}

		List<LineSegment2D_F32> found = grid.createSingleList();
		LineImageOps.mergeSimilar(found, (float) (Math.PI * 0.03), 5f);

		return found;
	}
}
