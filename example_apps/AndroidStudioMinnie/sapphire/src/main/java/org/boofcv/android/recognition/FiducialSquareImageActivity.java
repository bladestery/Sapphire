package org.boofcv.android.recognition;

import android.os.Bundle;

import java.util.List;

import boofcv.abst.fiducial.FiducialDetector;
import boofcv.abst.fiducial.SquareImage_to_FiducialDetector;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.filter.blur.BlurImageOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.misc.PixelMath;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.fiducial.ConfigFiducialImage;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.factory.filter.binary.ConfigThreshold;
import boofcv.factory.filter.binary.FactoryThresholdBinary;
import boofcv.factory.filter.binary.ThresholdType;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.shape.FactoryShapeDetector;
import boofcv.struct.image.GrayU8;
import sapphire.compiler.GIOGenerator;

/**
 * Detects and shows square binary fiducials
 *
 * @author Peter Abeles
 */
public class FiducialSquareImageActivity extends FiducialSquareActivity
{
	FiducialManager manager;
	List<FiducialManager.Info> list;

	public FiducialSquareImageActivity() {
		super(FiducialSquareImageHelpActivity.class);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		manager = new FiducialManager(this);
		manager.loadList();
		list = manager.copyList();

		if( list.size() == 0 ) {
			drawText = "ADD FIDUCIALS!";
		}
	}

	@Override
	protected void createDetector() {
		synchronized (lock) {
			dm.createFidSquare(robust, binaryThreshold, list);
		}

		/*
		SquareImage_to_FiducialDetector<GrayU8> detector;
		ConfigFiducialImage config = new ConfigFiducialImage();

		synchronized ( lock ) {
			ConfigThreshold configThreshold;
			if (robust) {
				configThreshold = ConfigThreshold.local(ThresholdType.LOCAL_SQUARE, 6);
			} else {
				configThreshold = ConfigThreshold.fixed(binaryThreshold);
			}
			detector = FactoryFiducial.squareImage(config, configThreshold, GrayU8.class, IT, FSD, FTB, FI, FDs, FIB);
		}
		*/

		for (int i = 0; i < list.size(); i++) {
			GrayU8 binary = manager.loadBinaryImage(list.get(i).id);
			dm.upBinaries(binary, list.get(i).sideLength);
			/*
			BIO.invert(binary,binary, ISC);
			PixelMath.multiply(binary,255,0,255,binary, ISC);
			detector.addPatternImage(binary,125,list.get(i).sideLength, GTIO, TIO, ISC, GIO, FI, FDs, IS, CI, FIB, IMO);
			*/
		}

		//return detector;
	}
}
