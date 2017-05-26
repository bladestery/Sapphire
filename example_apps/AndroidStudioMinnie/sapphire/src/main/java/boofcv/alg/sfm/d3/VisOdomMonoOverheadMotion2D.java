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

package boofcv.alg.sfm.d3;

import boofcv.abst.sfm.d2.ImageMotion2D;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.filter.binary.GThresholdImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
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
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.sfm.overhead.CreateSyntheticOverheadView;
import boofcv.alg.sfm.overhead.OverheadView;
import boofcv.alg.sfm.overhead.SelectOverheadParameters;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.sfm.FactorySfmMisc;
import boofcv.struct.calib.CameraPinholeRadial;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.se.Se2_F64;
import georegression.struct.se.Se3_F64;
import org.ejml.data.DenseMatrix64F;

/**
 * Estimates the motion of a monocular camera using the known transform between the camera and the ground plane.  The
 * camera's image is converted into an orthogonal overhead view.  Features are tracked inside the overhead image and
 * the 2D rigid body motion found.  The output can be either the 2D motion or the 3D motion of the camera. There is
 * no scale ambiguity since the transform from the plane to camera is known.
 *
 * The advantage of tracking inside the overhead view instead of the camera image is that the overhead view lacks
 * perspective distortion and is crops the image which does not contain the plane.  Features without perspective
 * distortion are easier to track and many false positives are removed by removing many features not on the plane.
 *
 * The plane which is being viewed is defined by the 'planeToCamera' transform.  In the plane's reference frame the
 * plane lies along the x-z axis and contains point (0,0,0).  See {@link CreateSyntheticOverheadView} for more
 * information about the ground plane coordinates and overhead image..
 *
 * @author Peter Abeles
 */
public class VisOdomMonoOverheadMotion2D<T extends ImageBase>
{
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	private static FactoryImageBorder FIB;
	// creates the overhead image
	private CreateSyntheticOverheadView<T> createOverhead;
	// estimates 2D motion inside the overhead image
	private ImageMotion2D<T,Se2_F64> motion2D;

	// storage for overhead image
	private OverheadView<T> overhead;

	// selects a reasonable overhead map
	private SelectOverheadParameters selectOverhead;

	// transform from the plane to the camera
	private Se3_F64 planeToCamera;

	// storage for intermediate results
	private Se2_F64 worldToCurr2D = new Se2_F64();
	private Se3_F64 worldToCurrCam3D = new Se3_F64();
	private Se3_F64 worldToCurr3D = new Se3_F64();
	private Se2_F64 temp = new Se2_F64();

	// adjust for offset
	private Se2_F64 origToMap = new Se2_F64();
	private Se2_F64 mapToOrigin = new Se2_F64();

	/**
	 * Configures motion estimation algorithm.
	 *
	 * @param cellSize Size of cells in plane in world units
	 * @param maxCellsPerPixel Specifies minimum resolution of a region in overhead image. A pixel in the camera
	 *                         can't overlap more than this number of map cells.   Higher values allow lower
	 *                         resolution regions.  Try 4.
	 * @param mapHeightFraction Reduce the map height by this fraction to avoid excessive unusable image space.  Set to
	 *                          1.0 to maximize the viewing area and any value less than one to crop it.
	 * @param motion2D Estimates motion inside the overhead image.
	 */
	public VisOdomMonoOverheadMotion2D(double cellSize,
									   double maxCellsPerPixel,
									   double mapHeightFraction ,
									   ImageMotion2D<T, Se2_F64> motion2D , ImageType<T> imageType )
	{
		selectOverhead = new SelectOverheadParameters(cellSize,maxCellsPerPixel,mapHeightFraction);
		this.motion2D = motion2D;

		createOverhead = FactorySfmMisc.createOverhead(imageType, FIB);

		overhead = new OverheadView<>(imageType.createImage(1, 1), 0, 0, cellSize);
	}

	/**
	 * Camera the camera's intrinsic and extrinsic parameters.  Can be called at any time.
	 * @param intrinsic Intrinsic camera parameters
	 * @param planeToCamera Transform from the plane to camera.
	 */
	public void configureCamera(CameraPinholeRadial intrinsic ,
								Se3_F64 planeToCamera ) {
		this.planeToCamera = planeToCamera;

		if( !selectOverhead.process(intrinsic,planeToCamera) )
			throw new IllegalArgumentException("Can't find a reasonable overhead map.  Can the camera view the plane?");

		overhead.centerX = selectOverhead.getCenterX();
		overhead.centerY = selectOverhead.getCenterY();

		createOverhead.configure(intrinsic,planeToCamera,overhead.centerX,overhead.centerY,overhead.cellSize,
				selectOverhead.getOverheadWidth(),selectOverhead.getOverheadHeight());

		// used to counter act offset in overhead image
		origToMap.set(overhead.centerX,overhead.centerY,0);
		mapToOrigin.set(-overhead.centerX,-overhead.centerY,0);

		// fill it so there aren't any artifacts in the left over
		overhead.image.reshape(selectOverhead.getOverheadWidth(), selectOverhead.getOverheadHeight());
		GIMO.fill(overhead.image,0, IMO);
	}

	/**
	 * Resets the algorithm into its initial state
	 */
	public void reset() {
		motion2D.reset();
	}

	/**
	 * Estimates the motion which the camera undergoes relative to the first frame processed.
	 *
	 * @param image Most recent camera image.
	 * @return true if motion was estimated or false if a fault occurred.  Should reset after a fault.
	 */
	public boolean process(T image, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						   GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						   ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						   GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryImageBorderAlgs FIBA, ImageBorderValue IBV,
						   FastHessianFeatureDetector FHFD, FactoryImageBorder FIB, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW, ImageType IT) {
		createOverhead.process(image, overhead.image);

		if( !motion2D.process(overhead.image,ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, FIBA, IBV, FHFD, FIB, FBF, CI, UW, IT) ) {
			return false;
		}

		worldToCurr2D.set(motion2D.getFirstToCurrent());
		worldToCurr2D.T.x *= overhead.cellSize;
		worldToCurr2D.T.y *= overhead.cellSize;

		// the true origin is offset from the overhead image which the transform is computed inside of
		origToMap.concat(worldToCurr2D,temp);
		temp.concat(mapToOrigin,worldToCurr2D);

		return true;
	}

	/**
	 * 2D motion.
	 *
	 * @return from world to current frame.
	 */
	public Se2_F64 getWorldToCurr2D() {
		return worldToCurr2D;
	}

	/**
	 * 3D motion.
	 *
	 * @return from world to current frame.
	 */
	public Se3_F64 getWorldToCurr3D() {
		// 2D to 3D coordinates
		worldToCurr3D.getT().set(-worldToCurr2D.T.y,0,worldToCurr2D.T.x);
		DenseMatrix64F R = worldToCurr3D.getR();

		// set rotation around Y axis.
		// Transpose the 2D transform since the rotation are pointing in opposite directions
		R.unsafe_set(0, 0, worldToCurr2D.c);
		R.unsafe_set(0, 2, -worldToCurr2D.s);
		R.unsafe_set(1, 1, 1);
		R.unsafe_set(2, 0, worldToCurr2D.s);
		R.unsafe_set(2, 2, worldToCurr2D.c);

		worldToCurr3D.concat(planeToCamera,worldToCurrCam3D);

		return worldToCurrCam3D;
	}

	/**
	 * Overhead image view
	 */
	public OverheadView<T> getOverhead() {
		return overhead;
	}

	/**
	 * 2D motion algorithm
	 */
	public ImageMotion2D<T, Se2_F64> getMotion2D() {
		return motion2D;
	}


}
