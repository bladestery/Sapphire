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
import boofcv.alg.fiducial.calib.circle.AsymmetricGridKeyPointDetections;
import boofcv.alg.fiducial.calib.circle.DetectAsymmetricCircleGrid;
import boofcv.alg.fiducial.calib.circle.EllipseClustersIntoAsymmetricGrid.Grid;
import boofcv.alg.fiducial.calib.circle.EllipsesIntoClusters;
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
import boofcv.alg.shapes.ellipse.BinaryEllipseDetector;
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
import org.ddogleg.struct.FastQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Calibration implementation of circle asymmetric grid fiducial.
 *
 * @see DetectAsymmetricCircleGrid
 * @see AsymmetricGridKeyPointDetections
 *
 * @author Peter Abeles
 */
public class CalibrationDetectorCircleAsymmGrid implements DetectorFiducialCalibration {
	// Detectors the grids
	private DetectAsymmetricCircleGrid<GrayF32> detector;
	// extracts key points from detected grid
	private AsymmetricGridKeyPointDetections keypoint = new AsymmetricGridKeyPointDetections();

	// Storage for 2D location of points on fiducial
	private List<Point2D_F64> layout;

	private CalibrationObservation results;

	double spaceToRadius;

	/**
	 * Configures the detector based on the pass in configuration class
	 * @param config Configuration for detector and target description
	 */
	public CalibrationDetectorCircleAsymmGrid(ConfigCircleAsymmetricGrid config, FactoryShapeDetector FSD, ImageType IT, FactoryThresholdBinary FTB) {

		InputToBinary<GrayF32> inputToBinary =
				FTB.threshold(config.thresholding,GrayF32.class, IT);

		BinaryEllipseDetector<GrayF32> ellipseDetector =
				FSD.ellipse(config.ellipse,GrayF32.class);

		spaceToRadius = (config.centerDistance/config.circleRadius);

		EllipsesIntoClusters e2c = new EllipsesIntoClusters(spaceToRadius*1.25,config.ellipseSizeSimilarity);

		detector = new DetectAsymmetricCircleGrid<>(config.numRows,config.numCols,inputToBinary,
				ellipseDetector,e2c);


		layout = createLayout(detector.getRows(),detector.getColumns(), config.centerDistance);
	}

	@Override
	public boolean process(GrayF32 input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BlIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						   ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, ImageType IT,
						   ImplBinaryInnerOps IBIO, ImplBinaryBorderOps IBBO, ImageBorderValue IBV, BinaryImageOps BIO, LinearContourLabelChang2004 cF) {
		results = new CalibrationObservation();
		detector.process(input, GBIO, ISC, GIO, BlIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT, cF);

		List<Grid> grids = detector.getGrids();

		if( grids.size() != 1 )
			return false;

		if( !keypoint.process(grids.get(0)) )
			return false;

		FastQueue<Point2D_F64> foundPixels = keypoint.getKeyPoints();

		for (int i = 0; i < foundPixels.size; i++) {
			results.add(foundPixels.get(i),i);
		}
		return true;
	}

	@Override
	public CalibrationObservation getDetectedPoints() {
		return results;
	}

	@Override
	public List<Point2D_F64> getLayout() {
		return layout;
	}

	/**
	 * Specifies the physical location of each point on the 2D calibration plane.  The fiducial is centered on the
	 * coordinate system
	 * @param numRows Number of rows
	 * @param numCols Number of columns
	 * @param centerDistance Space between each circle's center along x and y axis
	 * @return 2D locations
	 */
	public static List<Point2D_F64> createLayout( int numRows , int numCols , double centerDistance ) {

		List<Point2D_F64> ret = new ArrayList<>();

		double widthCell = centerDistance/2;

		double width = (numCols-1)*widthCell;
		double height = (numRows-1)*widthCell;

		for (int row = 0; row < numRows; row++) {
			double y = (numRows-row-1)*widthCell - height/2;
			for (int col = 0; col < numCols; col++) {
				double x = col*widthCell - width/2;

				if( row%2==0 && col%2==1)
					continue;
				if( row%2==1 && col%2==0)
					continue;
				ret.add( new Point2D_F64(x,y));
			}
		}

		return ret;
	}

	public DetectAsymmetricCircleGrid<GrayF32> getDetector() {
		return detector;
	}

	public AsymmetricGridKeyPointDetections getKeypointFinder() {
		return keypoint;
	}

	public int getRows() {
		return detector.getRows();
	}

	public int getColumns() {
		return detector.getColumns();
	}

	/**
	 * Distance between centers to circle radius ratio
	 */
	public double getSpaceToRadius() {
		return spaceToRadius;
	}
}
