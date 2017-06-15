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

package boofcv.factory.fiducial;

import boofcv.abst.fiducial.calib.*;
import boofcv.alg.fiducial.calib.chess.DetectChessboardFiducial;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.ImageType;

/**
 * Creates detectors of calibration targets.  These detectors return found key points in the image and
 * their known 2D location on the fiducial.
 *
 * @author Peter Abeles
 */
public class FactoryFiducialCalibration {

	/**
	 * Detector for a grid of square targets.   All squares must be entirely visible inside the image.
	 *
	 * @see boofcv.alg.fiducial.calib.grid.DetectSquareGridFiducial
	 *
	 * @param config Configuration for chessboard detector
	 * @return Square grid target detector.
	 */
	public static CalibrationDetectorSquareGrid squareGrid(ConfigSquareGrid config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {
		config.checkValidity();

		return new CalibrationDetectorSquareGrid(config, FSD, IT, FTB);
	}

	/**
	 * Detector for chessboard targets.  Squares can be partially outside, but inside corners must be entirely
	 * inside the image.
	 *
	 * @see DetectChessboardFiducial
	 *
	 * @param config Configuration for chessboard detector
	 * @return Square grid target detector.
	 */
	public static CalibrationDetectorChessboard chessboard(ConfigChessboard config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {
		config.checkValidity();

		return new CalibrationDetectorChessboard(config, FSD, IT, FTB);
	}

	/**
	 * Detector for a grid of binary targets.  Allows for squares to be obscured or partially outside of the
	 * image.
	 *
	 * @see CalibrationDetectorSquareFiducialGrid
	 *
	 * @param config Configuration of binary target
	 * @return Detector for binary grid target
	 */
	public static CalibrationDetectorSquareFiducialGrid binaryGrid(ConfigSquareGridBinary config, ImageType IT, FactoryShapeDetector FSD, FactoryThresholdBinary FTB, FactoryInterpolation FI,
																   FactoryDistort FDs, FactoryImageBorder FIB) {
		config.checkValidity();

		return new CalibrationDetectorSquareFiducialGrid(config, IT, FSD, FTB, FI, FDs, FIB);
	}

	/**
	 * Detector for asymmetric grid of circles.  All circles must be entirely inside of the image.
	 *
	 * @param config Configuration for target
	 * @return The detector
	 */
	public static CalibrationDetectorCircleAsymmGrid circleAsymmGrid( ConfigCircleAsymmetricGrid config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {
		config.checkValidity();

		return new CalibrationDetectorCircleAsymmGrid(config, FSD, IT, FTB);
	}
}
