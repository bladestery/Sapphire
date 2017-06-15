package org.boofcv.android.recognition;

import android.app.Dialog;
import android.os.Bundle;

import java.nio.file.spi.FileTypeDetector;

import boofcv.abst.fiducial.FiducialDetector;
import boofcv.abst.fiducial.calib.CalibrationPatterns;
import boofcv.abst.fiducial.calib.ConfigChessboard;
import boofcv.abst.fiducial.calib.ConfigCircleAsymmetricGrid;
import boofcv.abst.fiducial.calib.ConfigSquareGrid;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayU8;

/**
 * Detects calibration target fiducials
 */
public class FiducialCalibrationActivity extends FiducialSquareActivity {
	public static final int TARGET_DIALOG = 10;

	public static CalibrationPatterns targetType = CalibrationPatterns.CHESSBOARD;
	public static int numRows = 5;
	public static int numCols = 7;

	public FiducialCalibrationActivity() {
		super(FiducialCalibrationHelpActivity.class);
		disableControls = true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		showDialog(TARGET_DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case TARGET_DIALOG:
				final SelectCalibrationFiducial dialog = new SelectCalibrationFiducial(numRows,numCols,targetType);

				dialog.create(this, new Runnable() {
					@Override
					public void run() {
						numCols = dialog.getGridColumns();
						numRows = dialog.getGridRows();
						targetType = dialog.getGridType();

						changed = true;
						FiducialCalibrationActivity.this.startDetector();
					}
				});
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void createDetector() {
		dm.fidCalib(targetType, numCols, numRows);
		/*
		if( targetType == CalibrationPatterns.CHESSBOARD ) {
			ConfigChessboard config = new ConfigChessboard(numCols, numRows, 1);
			return FactoryFiducial.calibChessboard(config, GrayU8.class, FSD, IT, FTB);
		} else if( targetType == CalibrationPatterns.SQUARE_GRID ) {
			return FactoryFiducial.calibSquareGrid(new ConfigSquareGrid(numCols, numRows, 1,1), GrayU8.class, FSD, IT, FTB);
		} else if( targetType == CalibrationPatterns.CIRCLE_ASYMMETRIC_GRID ) {
			return FactoryFiducial.calibCircleAsymGrid(new ConfigCircleAsymmetricGrid(numCols, numRows, 1,6), GrayU8.class, FSD, IT, FTB);
		} else {
			throw new RuntimeException("Unknown");
		}
		*/
	}
}
