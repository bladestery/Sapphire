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

package boofcv.abst.fiducial.calib;

import boofcv.abst.filter.binary.InputToBinary;
import boofcv.abst.geo.calibration.DetectorFiducialCalibration;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.fiducial.calib.grid.DetectSquareGridFiducial;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.LinearContourLabelChang2004;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.binary.impl.ImplBinaryBorderOps;
import boofcv.alg.filter.binary.impl.ImplBinaryInnerOps;
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
import boofcv.alg.geo.calibration.CalibrationObservation;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.shapes.polygon.BinaryPolygonDetector;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link DetectorFiducialCalibration} for square grid target types.
 *
 * @author Peter Abeles
 */
public class CalibrationDetectorSquareGrid implements DetectorFiducialCalibration {

	DetectSquareGridFiducial<GrayF32> detect;

	List<Point2D_F64> layoutPoints;
	CalibrationObservation detected;

	public CalibrationDetectorSquareGrid(ConfigSquareGrid config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {

		if( config.refineWithCorners) {
			config.square.refine = config.configRefineCorners;
		} else {
			config.square.refine = config.configRefineLines;
		}

		double spaceToSquareRatio = config.spaceWidth/config.squareWidth;

		InputToBinary<GrayF32> inputToBinary =
				FTB.threshold(config.thresholding,GrayF32.class, IT);

		BinaryPolygonDetector<GrayF32> detectorSquare =
				FSD.polygon(config.square,GrayF32.class);

		detect = new DetectSquareGridFiducial<>(config.numRows,config.numCols,
				spaceToSquareRatio,inputToBinary,detectorSquare);

		layoutPoints = createLayout(config.numRows, config.numCols, config.squareWidth,config.spaceWidth);
	}

	@Override
	public boolean process(GrayF32 input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BlIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						   ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, ImageType IT,
						   ImplBinaryInnerOps IBIO, ImplBinaryBorderOps IBBO, ImageBorderValue IBV, BinaryImageOps BIO, LinearContourLabelChang2004 cF) {
		detected = new CalibrationObservation();
		if( detect.process(input, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT, cF) )  {
			List<Point2D_F64> found = detect.getCalibrationPoints();
			for (int i = 0; i < found.size(); i++) {
				detected.add( found.get(i), i );
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Creates a target that is composed of squares.  The squares are spaced out and each corner provides
	 * a calibration point.
	 *
	 * @param numRows Number of rows in calibration target. Must be odd.
	 * @param numCols Number of column in each calibration target.  Must be odd.
	 * @param squareWidth How wide each square is. Units are target dependent.
	 * @param spaceWidth Distance between the sides on each square.  Units are target dependent.
	 * @return Target description
	 */
	public static List<Point2D_F64> createLayout(int numRows, int numCols, double squareWidth, double spaceWidth)
	{
		List<Point2D_F64> all = new ArrayList<>();

		double width = (numCols*squareWidth + (numCols-1)*spaceWidth);
		double height = (numRows*squareWidth + (numRows-1)*spaceWidth);

		double startX = -width/2;
		double startY = -height/2;

		for( int i = numRows-1; i >= 0; i-- ) {
			// this will be on the top of the black in the row
			double y = startY + i*(squareWidth+spaceWidth)+squareWidth;

			List<Point2D_F64> top = new ArrayList<>();
			List<Point2D_F64> bottom = new ArrayList<>();

			for( int j = 0; j < numCols; j++ ) {
				double x = startX + j*(squareWidth+spaceWidth);

				top.add( new Point2D_F64(x,y));
				top.add( new Point2D_F64(x+squareWidth,y));
				bottom.add( new Point2D_F64(x,y-squareWidth));
				bottom.add( new Point2D_F64(x + squareWidth, y - squareWidth));
			}

			all.addAll(top);
			all.addAll(bottom);
		}

		return all;
	}

	@Override
	public CalibrationObservation getDetectedPoints() {
		return detected;
	}

	@Override
	public List<Point2D_F64> getLayout() {
		return layoutPoints;
	}

	public DetectSquareGridFiducial<GrayF32> getAlgorithm() {
		return detect;
	}

	/**
	 * Returns number of rows in the grid
	 * @return number of rows
	 */
	public int getGridRows() {
		return detect.getRows();
	}

	/**
	 * Returns number of columns in the grid
	 * @return number of columns
	 */
	public int getGridColumns() {
		return detect.getColumns();
	}

	/**
	 * Returns number of rows in calibration point grid
	 * @return number of rows
	 */
	public int getPointRows() {
		return detect.getCalibrationRows();
	}

	/**
	 * Returns number of columns in calibration point grid
	 * @return number of columns
	 */
	public int getPointColumns() {
		return detect.getCalibrationCols();
	}
}
