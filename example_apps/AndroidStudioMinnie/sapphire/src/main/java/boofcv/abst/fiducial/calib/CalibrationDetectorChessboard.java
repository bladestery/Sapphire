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
import boofcv.alg.fiducial.calib.chess.DetectChessboardFiducial;
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
import boofcv.alg.shapes.polygon.RefineBinaryPolygon;
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
 * Wrapper around {@link DetectChessboardFiducial} for {@link DetectorFiducialCalibration}
 * 
 * @author Peter Abeles
 */
public class CalibrationDetectorChessboard implements DetectorFiducialCalibration {
	DetectChessboardFiducial<GrayF32> alg;

	List<Point2D_F64> layoutPoints;
	CalibrationObservation detected;

	public CalibrationDetectorChessboard(ConfigChessboard config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {

		RefineBinaryPolygon<GrayF32> refineLine =
				FSD.refinePolygon(config.configRefineLines,GrayF32.class);
		RefineBinaryPolygon<GrayF32> refineCorner = config.refineWithCorners ?
				FSD.refinePolygon(config.configRefineLines,GrayF32.class) : null;

		config.square.refine = null;

		BinaryPolygonDetector<GrayF32> detectorSquare =
				FSD.polygon(config.square,GrayF32.class);

		InputToBinary<GrayF32> inputToBinary =
				FTB.threshold(config.thresholding,GrayF32.class, IT);

		alg = new DetectChessboardFiducial<>(
				config.numRows, config.numCols, config.maximumCornerDistance,detectorSquare,
				refineLine,refineCorner,inputToBinary);

		layoutPoints = gridChess(config.numRows, config.numCols, config.squareWidth);
	}

	@Override
	public boolean process(GrayF32 input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BlIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						   ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, ImageType IT,
						   ImplBinaryInnerOps IBIO, ImplBinaryBorderOps IBBO, ImageBorderValue IBV, BinaryImageOps BIO, LinearContourLabelChang2004 cF) {
		detected = new CalibrationObservation();
		if( alg.process(input, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT, IBIO, IBBO, IBV, BIO, cF) )  {
			List<Point2D_F64> found = alg.getCalibrationPoints();
			for (int i = 0; i < found.size(); i++) {
				detected.add( found.get(i) , i );
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public CalibrationObservation getDetectedPoints() {
		return detected;
	}

	@Override
	public List<Point2D_F64> getLayout() {
		return layoutPoints;
	}

	/**
	 * Returns number of rows in the chessboard grid
	 * @return number of rows
	 */
	public int getGridRows() {
		return alg.getRows();
	}

	/**
	 * Returns number of columns in the chessboard grid
	 * @return number of columns
	 */
	public int getGridColumns() {
		return alg.getColumns();
	}


	public DetectChessboardFiducial<GrayF32> getAlgorithm() {
		return alg;
	}

	/**
	 * This target is composed of a checkered chess board like squares.  Each corner of an interior square
	 * touches an adjacent square, but the sides are separated.  Only interior square corners provide
	 * calibration points.
	 *
	 * @param numRows Number of grid rows in the calibration target
	 * @param numCols Number of grid columns in the calibration target
	 * @param squareWidth How wide each square is.  Units are target dependent.
	 * @return Target description
	 */
	public static List<Point2D_F64> gridChess(int numRows, int numCols, double squareWidth)
	{
		List<Point2D_F64> all = new ArrayList<>();

		// convert it into the number of calibration points
		numCols = numCols - 1;
		numRows = numRows - 1;

		// center the grid around the origin. length of a size divided by two
		double startX = -((numCols-1)*squareWidth)/2.0;
		double startY = -((numRows-1)*squareWidth)/2.0;

		for( int i = numRows-1; i >= 0; i-- ) {
			double y = startY+i*squareWidth;
			for( int j = 0; j < numCols; j++ ) {
				double x = startX+j*squareWidth;
				all.add( new Point2D_F64(x,y));
			}
		}

		return all;
	}
}
