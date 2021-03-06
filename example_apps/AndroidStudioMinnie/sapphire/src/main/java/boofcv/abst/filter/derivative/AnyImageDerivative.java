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

package boofcv.abst.filter.derivative;

import boofcv.abst.filter.convolve.ConvolveInterface;
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
import boofcv.alg.filter.kernel.GKernelMath;
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
import boofcv.struct.convolve.Kernel2D;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * <p>
 * A helpful class which allows a derivative of any order to be computed from an input image using a simple to use
 * interface.  Higher order derivatives are computed from lower order derivatives.  Derivatives are computed
 * using convolution kernels and thus might not be as efficient as when using functions from
 * {@link boofcv.factory.filter.derivative.FactoryDerivative}.
 * </p>
 *
 * @author Peter Abeles
 */
public class AnyImageDerivative<I extends ImageGray, D extends ImageGray> {
	// filters for computing image derivatives
	private ConvolveInterface<I, D> derivX;
	private ConvolveInterface<I, D> derivY;

	// gaussian blur the derivative image
	private ConvolveInterface<D, D> derivDerivX;
	private ConvolveInterface<D, D> derivDerivY;

	// how the borders are handled
	private BorderType borderDeriv = BoofDefaults.DERIV_BORDER_TYPE;

	private I inputImage;

		// stores computed derivative images
	private D[][] derivatives;
	// if true then
	private boolean[][] stale;

	private Class<D> derivType;

	/**
	 * Constructor for 1D kernels.
	 *
	 * @param deriv 1D convolution kernel for computing derivative along x and y axises.
	 * @param inputType The type of input image.
	 * @param derivType Derivative image type
	 */
	public AnyImageDerivative(Kernel1D deriv , Class<I> inputType , Class<D> derivType, FactoryImageBorder FIB)
	{
		this.derivType = derivType;

		derivX = FactoryConvolve.convolve(deriv,inputType,derivType, borderDeriv,true, FIB);
		derivY = FactoryConvolve.convolve(deriv,inputType,derivType, borderDeriv,false, FIB);

		derivDerivX = FactoryConvolve.convolve(deriv,derivType,derivType, borderDeriv,true, FIB);
		derivDerivY = FactoryConvolve.convolve(deriv,derivType,derivType, borderDeriv,false, FIB);
	}

	/**
	 * Constructor for 2D kernels.
	 *
	 * @param derivX 2D convolution kernel for computing derivative along x axis
	 * @param inputType The type of input image.
	 * @param derivType Derivative image type
	 */
	public AnyImageDerivative( Kernel2D derivX , Class<I> inputType , Class<D> derivType, FactoryImageBorder FIB)
	{
		this.derivType = derivType;
		Kernel2D derivY = GKernelMath.transpose(derivX);

		this.derivX = FactoryConvolve.convolve(derivX,inputType,derivType, borderDeriv, FIB);
		this.derivY = FactoryConvolve.convolve(derivY,inputType,derivType, borderDeriv, FIB);

		derivDerivX = FactoryConvolve.convolve(derivX,derivType,derivType, borderDeriv, FIB);
		derivDerivY = FactoryConvolve.convolve(derivY,derivType,derivType, borderDeriv, FIB);
	}

	/**
	 * Constructor for when all derivative filters are specified
	 *
	 * @param derivX Filter for computing derivative along x axis from input image.
	 * @param derivY Filter for computing derivative along y axis from input image.
	 * @param derivXX Filter for computing derivative along x axis from input image.
	 * @param derivYY Filter for computing derivative along y axis from input image.
	 * @param inputType The type of input image.
	 */
	public AnyImageDerivative(ConvolveInterface<I, D> derivX , ConvolveInterface<I, D> derivY ,
							  ConvolveInterface<D, D> derivXX , ConvolveInterface<D, D> derivYY ,
							  Class<I> inputType , Class<D> derivType )
	{
		this.derivType = derivType;

		this.derivX = derivX;
		this.derivY = derivY;

		this.derivDerivX = derivXX;
		this.derivDerivY = derivYY;
	}

	/**
	 * Sets the new input image from which the image derivatives are computed from.
	 *
	 * @param input Input image.
	 */
	public void setInput( I input ) {
		this.inputImage = input;

		// reset the state flag so that everything need sto be computed
		if( stale != null ) {
			for( int i = 0; i < stale.length; i++) {
				boolean a[] = stale[i];
				for( int j = 0; j < a.length; j++ ) {
					a[j] = true;
				}
			}
		}
	}

	/**
	 * Computes derivative images using previously computed lower level derivatives.  Only
	 * computes/declares images as needed.
	 */
	public D getDerivative(GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN,
						   ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						   ConvertImage CI, UtilWavelet UW, ImageType IT, boolean... isX) {
		if( derivatives == null ) {
			declareTree(isX.length, GIO);
		} else if( isX.length > stale.length ) {
			growTree(isX.length, GIO);
		}

		int index = 0;
		int prevIndex = 0;
		for( int level = 0; level < isX.length; level++ ) {
			index |= isX[level] ? 0 : 1 << level;

			if( stale[level][index] ) {
				stale[level][index] = false;
				derivatives[level][index].reshape(inputImage.getWidth(),inputImage.getHeight());

				if( level == 0 ) {
					if( isX[level]) {
						derivX.process(inputImage,derivatives[level][index], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
					} else {
						derivY.process(inputImage,derivatives[level][index], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
					}
				} else {
					D prev = derivatives[level-1][prevIndex];
					if( isX[level]) {
						derivDerivX.process(prev,derivatives[level][index], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
					} else {
						derivDerivY.process(prev,derivatives[level][index], GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, IT);
					}
				}
			}
			prevIndex = index;
		}

		return derivatives[isX.length-1][index];
	}

	private void declareTree( int maxDerivativeOrder, GeneralizedImageOps GIO) {
		derivatives = (D[][])new ImageGray[maxDerivativeOrder][];
		stale = new boolean[maxDerivativeOrder][];

		for( int i = 0; i < maxDerivativeOrder; i++) {
			int N = (int)Math.pow(2,i+1);
			derivatives[i] = (D[])new ImageGray[N];
			stale[i] = new boolean[N];
			for( int j = 0; j < N; j++ ) {
				stale[i][j] = true;
				derivatives[i][j] = GIO.createSingleBand(derivType,1,1);
			}
		}
	}

	private void growTree( int maxDerivativeOrder, GeneralizedImageOps GIO) {
		D[][] oldDerives = derivatives;
		boolean[][] oldStale = stale;

		declareTree(maxDerivativeOrder, GIO);

		int N = oldStale.length;
		for( int i = 0; i < N; i++ ) {
			int M = oldStale[i].length;
			for( int j = 0; j < M; j++ ) {
				derivatives[i][j] = oldDerives[i][j];
				stale[i][j] = oldStale[i][j];
			}
		}

	}
}
