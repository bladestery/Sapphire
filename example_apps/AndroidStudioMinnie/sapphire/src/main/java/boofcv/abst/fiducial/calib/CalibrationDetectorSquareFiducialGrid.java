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

import boofcv.abst.geo.calibration.DetectorFiducialCalibration;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.fiducial.calib.DetectFiducialSquareGrid;
import boofcv.alg.fiducial.square.DetectFiducialSquareBinary;
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
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_F64;

import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around {@link DetectFiducialSquareGrid} for {@link DetectorFiducialCalibration}.
 *
 * @author Peter Abeles
 */
public class CalibrationDetectorSquareFiducialGrid implements DetectorFiducialCalibration {
	// number of squares along each grid axis
	int numRows;
	int numCols;

	// number of calibration points along each grid axis
	int numPointRows;
	int numPointCols;

	// layout of points in fiducial frame
	List<Point2D_F64> layoutPoints;

	// square binary fiducial detector
	DetectFiducialSquareGrid<GrayF32> detector;

	// storage for observations
	CalibrationObservation observations;

	public CalibrationDetectorSquareFiducialGrid(ConfigSquareGridBinary config, ImageType IT, FactoryShapeDetector FSD, FactoryThresholdBinary FTB, FactoryInterpolation FI,
												 FactoryDistort FDs, FactoryImageBorder FIB) {

		DetectFiducialSquareBinary<GrayF32> fiducialDetector = FactoryFiducial.
				squareBinary(config.configDetector, config.configThreshold, GrayF32.class, IT, FSD, FTB, FI, FDs, FIB).getAlgorithm();
		detector = new DetectFiducialSquareGrid<>(config.numRows,config.numCols,config.ids,fiducialDetector);

		numRows = config.numRows;
		numCols = config.numCols;

		numPointRows = 2*numRows;
		numPointCols = 2*numCols;

		layoutPoints = CalibrationDetectorSquareGrid.createLayout(numRows, numCols, config.squareWidth, config.spaceWidth);
	}

	@Override
	public boolean process(GrayF32 input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BlIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						   ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, ImageType IT,
						   ImplBinaryInnerOps IBIO, ImplBinaryBorderOps IBBO, ImageBorderValue IBV, BinaryImageOps BIO, LinearContourLabelChang2004 cF) {
		observations = new CalibrationObservation();
		if( !detector.detect(input, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT, cF) ) {
			return false;
		}

		List<DetectFiducialSquareGrid.Detection> detections = detector.getDetections();

		for (int i = 0; i < detections.size(); i++) {
			DetectFiducialSquareGrid.Detection d = detections.get(i);

			int row = d.gridIndex/numCols;
			int col = d.gridIndex%numCols;

			int pointRow = row*2;
			int pointCol = col*2;

			observations.add(d.location.a,getPointIndex(pointRow,   pointCol));
			observations.add(d.location.b,getPointIndex(pointRow,   pointCol+1));
			observations.add(d.location.c,getPointIndex(pointRow+1, pointCol+1));
			observations.add(d.location.d,getPointIndex(pointRow+1, pointCol));
		}

		observations.sort();

		return true;
	}

	private int getPointIndex( int row , int col ) {
		return row*numPointCols + col;
	}

	@Override
	public CalibrationObservation getDetectedPoints() {
		return observations;
	}

	@Override
	public List<Point2D_F64> getLayout() {
		return layoutPoints;
	}
}
