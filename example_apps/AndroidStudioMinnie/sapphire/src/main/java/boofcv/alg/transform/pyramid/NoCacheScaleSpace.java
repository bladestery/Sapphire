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

package boofcv.alg.transform.pyramid;

import boofcv.abst.filter.convolve.ConvolveInterface;
import boofcv.abst.filter.derivative.AnyImageDerivative;
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
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.convolve.FactoryConvolve;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.BoofDefaults;
import boofcv.struct.convolve.Kernel1D;
import boofcv.struct.gss.GaussianScaleSpace;
import boofcv.struct.image.ImageGray;


/**
 * <p>
 * Implementation of {@link boofcv.struct.gss.GaussianScaleSpace} that focuses on one scale space at a time.
 * When the scale space is changed the scaled image is recomputed and previously computed derivatives
 * are marked as stale.  Then the derivatives are recomputed as needed.
 * </p>
 *
 * @author Peter Abeles
 */
public class NoCacheScaleSpace<I extends ImageGray, D extends ImageGray>
		implements GaussianScaleSpace<I,D>
{
	private static FactoryKernelGaussian FKG;
	private static GeneralizedImageOps GIO;
	private static GBlurImageOps GBIO;
	private static InputSanityCheck ISC;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static ConvolveNormalized CN;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	private static FactoryImageBorder FIB;
	private static ConvolveJustBorder_General CJBG;
	private static ConvertImage CI;
	private static UtilWavelet UW;
	// reference to the original input image
	private I originalImage;

	// types of input images
	private Class<I> inputType;

	AnyImageDerivative<I,D> anyDeriv;

	private double scales[];
	private int currentScale;

	private I workImage;
	private I scaledImage;


	// how the borders are handled
	BorderType borderDeriv = BoofDefaults.DERIV_BORDER_TYPE;
	BorderType borderBlur = BorderType.NORMALIZED;

	/**
	 * Declares internal data structures.
	 *
	 * @param inputType Type of input image
	 * @param derivType Derivative image type.
	 */
	public NoCacheScaleSpace(Class<I> inputType, Class<D> derivType) {
		this.inputType = inputType;
		anyDeriv = GImageDerivativeOps.derivativeForScaleSpace(inputType, derivType, GIO, FIB);
	}

	@Override
	public void setScales(double... scales) {
		this.scales = scales;
	}

	@Override
	public double getScale(int level) {
		return scales[level];
	}

	@Override
	public void setImage(I input) {
		this.originalImage = input;

		if( scaledImage == null ) {
			scaledImage = GIO.createSingleBand(inputType, input.getWidth(), input.getHeight());
			workImage = GIO.createSingleBand(inputType, input.getWidth(), input.getHeight());
		} else if( scaledImage.width != input.width || scaledImage.height != input.height ) {
			scaledImage.reshape(input.width,input.height);
			workImage.reshape(input.width,input.height);
		}
	}

	@Override
	public void setActiveScale(int index) {
		this.currentScale = index;
		double sigma = scales[index];
		int radius = FKG.radiusForSigma(sigma, 0);

		Kernel1D kernel = FKG.gaussian1D(inputType,sigma,radius, GIO);

		ConvolveInterface<I, I> blurX = FactoryConvolve.convolve(kernel,inputType,inputType, borderBlur ,true, FIB);
		ConvolveInterface<I, I> blurY = FactoryConvolve.convolve(kernel,inputType,inputType, borderBlur ,false, FIB);

		// compute the scale image
		blurX.process(originalImage,workImage, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW);
		blurY.process(workImage,scaledImage, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW);

		anyDeriv.setInput(scaledImage);
	}

	@Override
	public double getCurrentScale() {
		return scales[currentScale];
	}

	@Override
	public int getTotalScales() {
		return scales.length;
	}

	@Override
	public I getScaledImage() {
		return scaledImage;
	}

	@Override
	public void setBorderType(BorderType type) {
		borderDeriv = type;
		borderBlur = type;
		setActiveScale(currentScale);
	}

	@Override
	public BorderType getBorderType() {
		return borderDeriv;
	}

	/**
	 * Computes derivative images using previously computed lower level derivatives.  Only
	 * computes/declares images as needed.
	 */
	@Override
	public D getDerivative(boolean... isX) {
		return anyDeriv.getDerivative(GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, isX);
	}
}
