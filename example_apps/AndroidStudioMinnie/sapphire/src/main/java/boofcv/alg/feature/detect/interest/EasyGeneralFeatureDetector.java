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

package boofcv.alg.feature.detect.interest;

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.abst.filter.derivative.ImageHessian;
import boofcv.alg.InputSanityCheck;
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
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.ImageGray;
import sapphire.app.SapphireObject;
import sun.java2d.loops.DrawGlyphListAA;

/**
 * Detects features using {@link GeneralFeatureDetector} but Handles all the derivative computations automatically.
 *
 * @author Peter Abeles
 */
public class EasyGeneralFeatureDetector<T extends ImageGray, D extends ImageGray> implements SapphireObject{
	// Feature detector
	protected GeneralFeatureDetector<T, D> detector;
	// Computes image gradient
	protected ImageGradient<T, D> gradient;
	// computes hessian
	protected ImageHessian<D> hessian;

	// storage for image derivatives
	protected D derivX; // first derivative x-axis
	protected D derivY; // first derivative y-axis
	protected D derivXX; // second derivative x-x
	protected D derivYY; // second derivative y-y
	protected D derivXY; // second derivative x-y

	/**
	 * Configures detector and uses default image derivatives.
	 *
	 * @param detector Feature detector.
	 * @param imageType Type of input image
	 * @param derivType If null then the derivative will be selected using the image type.
	 */
	public EasyGeneralFeatureDetector(GeneralFeatureDetector<T, D> detector ,
									  Class<T> imageType, Class<D> derivType, FactoryDerivative FD, GeneralizedImageOps GIO, FactoryImageBorder FIB ) {
		this.detector = detector;

		if( derivType == null ) {
			derivType = GImageDerivativeOps.getDerivativeType(imageType);
		}

		if( detector.getRequiresGradient() || detector.getRequiresHessian()  ) {
			gradient = FD.sobel(imageType, derivType, GIO, FIB);
		}
		if( detector.getRequiresHessian() ) {
			hessian = FD.hessianSobel(derivType, GIO, FIB);
		}
		declareDerivativeImages(gradient, hessian, derivType, GIO);
	}

	/**
	 * Constructor which allows the user to specify how derivatives are computed
	 */
	public EasyGeneralFeatureDetector(GeneralFeatureDetector<T, D> detector,
									  ImageGradient<T, D> gradient,
									  ImageHessian<D> hessian,
									  Class<D> derivType, GeneralizedImageOps GIO) {
		this.detector = detector;
		this.gradient = gradient;
		this.hessian = hessian;

		declareDerivativeImages(gradient, hessian, derivType, GIO);
	}

	/**
	 * Declare storage for image derivatives as needed
	 */
	private void declareDerivativeImages(ImageGradient<T, D> gradient, ImageHessian<D> hessian, Class<D> derivType, GeneralizedImageOps GIO) {
		if( gradient != null || hessian != null ) {
			derivX = GIO.createSingleBand(derivType, 1, 1);
			derivY = GIO.createSingleBand(derivType,1,1);
		}
		if( hessian != null ) {
			derivXX = GIO.createSingleBand(derivType,1,1);
			derivYY = GIO.createSingleBand(derivType,1,1);
			derivXY = GIO.createSingleBand(derivType,1,1);
		}
	}

	/**
	 * Detect features inside the image.  Excluding points in the exclude list.
	 *
	 * @param input Image being processed.
	 * @param exclude List of points that should not be returned.
	 */
	public void detect(T input, QueueCorner exclude, InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO,
					   GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
					   GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
					   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
					   ThresholdImageOps TIO) {

		initializeDerivatives(input);

		if (detector.getRequiresGradient() || detector.getRequiresHessian())
			gradient.process(input, derivX, derivY, ISC,DHF, CINB, CJBG,GSO,GSUO);
		if (detector.getRequiresHessian())
			hessian.process(derivX, derivY, derivXX, derivYY, derivXY, ISC, DHF, CINB, CJBG, GSO, GSUO);

		detector.setExcludeMaximum(exclude);
		detector.process(input, derivX, derivY, derivXX, derivYY, derivXY, GIMO, IMO, ISC, CNN, CINB, CNJB, CN,
				GBIO, GIO, BIO, CIM, FKG, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO);
	}

	/**
	 * Reshape derivative images to match the input image
	 */
	private void initializeDerivatives(T input) {
		// reshape derivatives if the input image has changed size
		if (detector.getRequiresGradient() || detector.getRequiresHessian()) {
			derivX.reshape(input.width, input.height);
			derivY.reshape(input.width, input.height);
		}
		if (detector.getRequiresHessian()) {
			derivXX.reshape(input.width, input.height);
			derivYY.reshape(input.width, input.height);
			derivXY.reshape(input.width, input.height);
		}
	}

	public GeneralFeatureDetector<T, D> getDetector() {
		return detector;
	}

	public QueueCorner getMaximums() {
		return detector.getMaximums();
	}

	public QueueCorner getMinimums() {
		return detector.getMinimums();
	}
}
