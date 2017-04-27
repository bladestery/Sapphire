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

import android.renderscript.ScriptGroup;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.GConvolveImageOps;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.ImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.convolve.Kernel1D;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;



/**
 * Finds the derivative using a Gaussian kernel.  This is the same as convolving the image
 * and then computing the derivative
 *
 * @author Peter Abeles
 */
public class ImageGradient_Gaussian<I extends ImageGray, D extends ImageGray>
		implements ImageGradient<I, D> {
	private static FactoryImageBorder FIB;
	private static FactoryKernelGaussian FKG;
	private static GeneralizedImageOps GIO;
	// default border.
	private BorderType borderType = BorderType.EXTENDED;
	ImageBorder border;

	// storage the results after the first gaussian blur
	private I storage;

	// type of input/output images
	private Class<D> derivType;

	Kernel1D kernelBlur;
	Kernel1D kernelDeriv;

	double maxValue;

	public ImageGradient_Gaussian(int radius , Class<I> inputType , Class<D> derivType) {
		this(FKG.sigmaForRadius(radius,0),radius,inputType,derivType);
	}

	public ImageGradient_Gaussian(double sigma, int radius,
								  Class<I> inputType , Class<D> derivType ) {
		this.derivType = derivType;

		// need to do this here to make sure the blur and derivative functions have the same paramters.
		if( radius <= 0 )
			radius = FKG.radiusForSigma(sigma,1);
		else if( sigma <= 0 )
			sigma = FKG.sigmaForRadius(radius,1);

		kernelBlur = FKG.gaussian1D(inputType,sigma,radius, GIO);
		kernelDeriv = FKG.derivativeI(inputType,1,sigma,radius, GIO);
		border = FIB.single(derivType, borderType);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public void process(I inputImage , D derivX, D derivY , InputSanityCheck ISC, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO) {

		if( storage == null ) {
			storage = (I)inputImage.createNew(inputImage.width,inputImage.height );
		} else {
			storage.reshape(inputImage.width,inputImage.height);
		}

		GConvolveImageOps.verticalNormalized(kernelBlur,inputImage,storage);
		GConvolveImageOps.horizontal(kernelDeriv,storage,derivX,border );
		GConvolveImageOps.horizontalNormalized(kernelBlur,inputImage,storage);
		GConvolveImageOps.vertical(kernelDeriv,storage,derivY,border );
	}

	@Override
	public void setBorderType(BorderType type, FactoryImageBorder FIB) {
		this.borderType = type;
		border = FIB.single(derivType, borderType);
	}

	@Override
	public BorderType getBorderType() {
		return borderType;
	}

	@Override
	public int getBorder() {
		return 0;
	}

	@Override
	public ImageType<D> getDerivativeType(ImageType IT) {
		return  IT.single(derivType);
	}
}