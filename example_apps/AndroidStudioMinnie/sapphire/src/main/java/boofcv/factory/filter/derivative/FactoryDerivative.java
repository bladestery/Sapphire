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

package boofcv.factory.filter.derivative;

import android.renderscript.ScriptGroup;

import org.hamcrest.Factory;

import boofcv.abst.filter.derivative.*;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.enhance.GEnhanceImageOps;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.derivative.*;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.ImageBorder_F32;
import boofcv.core.image.border.ImageBorder_S32;
import boofcv.struct.image.*;
import sapphire.app.SapphireObject;

import java.lang.reflect.Method;

/**
 * <p>
 * Factory for creating different types of {@link boofcv.abst.filter.derivative.ImageGradient}, which are used to compute
 * the image's derivative.
 * </p>
 *
 * @author Peter Abeles
 */
public class FactoryDerivative implements SapphireObject {
	public FactoryDerivative() {}
	/**
	 * Computes the image gradient inside a multi-band image then reduces the output to a single
	 * band before returning the results
	 *
	 * @param gradient Computes the multi-band image gradient
	 * @param type Specifies which method is to be used to reduce the output into single band image
	 * @param outputType Type of output image
	 * @param <I> Input type
	 * @param <M> Intermediate type
	 * @param <D> Output type
	 * @return Gradient
	 */
	public <I extends ImageMultiBand, M extends ImageMultiBand, D extends ImageGray>
	ImageGradient<I,D> gradientReduce( ImageGradient<I,M> gradient ,
									   DerivativeReduceType type,
									   Class<D> outputType, ImageType IT)
	{

		String name;

		switch( type ) {
			case MAX_F: name = "maxf"; break;
			default:
				throw new RuntimeException("Unknown reduce type "+type);
		}

		Class middleType;
		switch( gradient.getDerivativeType(IT).getFamily()) {
			case PLANAR:
				middleType = Planar.class;
				break;

			case GRAY:
				throw new IllegalArgumentException("Can't have gradient output be single band");

			default:
				middleType = gradient.getDerivativeType(IT).getImageClass();
		}


		Method m = findReduce(name,middleType, outputType);
		GradientMultiToSingleBand_Reflection<M,D> reducer =
				new GradientMultiToSingleBand_Reflection<>(m, gradient.getDerivativeType(IT), outputType);

		return new ImageGradientThenReduce<>(gradient, reducer, IT);
	}

	/**
	 * Returns the gradient for single band images of the specified type
	 * @param type Type of gradient
	 * @param inputType Type of input image
	 * @param derivType Type of gradient image.  null for default
	 * @param <I> Input image
	 * @param <D> Derivative image
	 * @return gradient filter
	 */
	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> gradientSB( DerivativeType type , Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);

		Class which;

		switch( type ) {
			case PREWITT:
				which = GradientPrewitt.class;
				break;

			case SOBEL:
				which = GradientSobel.class;
				break;

			case THREE:
				which = GradientThree.class;
				break;

			case TWO_0:
				which = GradientTwo0.class;
				break;

			case TWO_1:
				which = GradientTwo1.class;
				break;

			default:
				throw new IllegalArgumentException("Unknown type "+type);
		}

		Method m = findDerivative(which,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	/**
	 * Filters for computing the gradient of {@link Planar} images.
	 *
	 * @param type Which gradient to compute
	 * @param numBands Number of bands in the image
	 * @param inputType Type of data on input
	 * @param derivType Type of data on output (null for default)
	 * @param <I> Image type
	 * @param <D> Derivative type
	 * @return the filter
	 */
	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<Planar<I>,Planar<D>>
	gradientPL(DerivativeType type , int numBands , Class<I> inputType , Class<D> derivType, ImageType IT, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		ImageGradient<I,D> g = gradientSB(type,inputType,derivType, GIO, FIB);
		return new ImageGradient_PL<>(g, numBands, IT);
	}


	public <I extends ImageBase, D extends ImageBase>
	ImageGradient<I,D>
	gradient(DerivativeType type , ImageType<I> inputType , ImageType<D> derivType, ImageType IT, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType != null ) {
			if( inputType.getFamily() != derivType.getFamily() )
				throw new IllegalArgumentException("input and output must be of the same family");
		}

		switch( inputType.getFamily() ) {
			case GRAY: {
				Class derivClass = derivType != null ? derivType.getImageClass() : null;
				return gradientSB(type,inputType.getImageClass(),derivClass, GIO, FIB);
			}

			case PLANAR: {
				int numBands = inputType.getNumBands();
				Class derivClass = derivType != null ? derivType.getImageClass() : null;
				return gradientPL(type,numBands,inputType.getImageClass(),derivClass, IT, GIO, FIB);
			}

			case INTERLEAVED:
				throw new IllegalArgumentException("INTERLEAVED images not yet supported");

			default:
				throw new IllegalArgumentException("Unknown image type");
		}
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> prewitt( Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);

		Method m = findDerivative(GradientPrewitt.class,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> sobel( Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);

		Method m = findSobelDerivative(GradientSobel.class,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> three(Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		Method m = findThreeDerivative(GradientThree.class,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> two0(Class<I> inputType, Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		Method m = findDerivative(GradientTwo0.class,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> two1(Class<I> inputType, Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		Method m = findDerivative(GradientTwo1.class,inputType,derivType, GIO);
		return new ImageGradient_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageHessianDirect<I,D> hessianDirectThree( Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		Method m = findHessian(HessianThree.class,inputType,derivType, GIO);
		return new ImageHessianDirect_Reflection<>(m);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageHessianDirect<I,D> hessianDirectSobel( Class<I> inputType , Class<D> derivType, GeneralizedImageOps GIO)
	{
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		Method m = findHessian(HessianSobel.class,inputType,derivType, GIO);
		return new ImageHessianDirect_Reflection<>(m);
	}

	public <D extends ImageGray>
	ImageHessian<D> hessian( Class<?> gradientType , Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		Method m = FindHessianFromGradient(gradientType,derivType, GIO);
		return new ImageHessian_Reflection<>(m, FIB);
	}

	public <I extends ImageGray, D extends ImageGray>
	ImageGradient<I,D> gaussian( double sigma , int radius , Class<I> inputType , Class<D> derivType) {
		if( derivType == null )
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		return new ImageGradient_Gaussian<>(sigma, radius, inputType, derivType);
	}

	public <D extends ImageGray> ImageHessian<D> hessianSobel(Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		if( derivType == GrayF32.class )
			return (ImageHessian<D>)hessian(GradientSobel.class,GrayF32.class, GIO, FIB);
		else if( derivType == GrayS16.class )
			return (ImageHessian<D>)hessian(GradientSobel.class,GrayS16.class, GIO, FIB);
		else
			throw new IllegalArgumentException("Not supported yet");
	}

	public <D extends ImageGray> ImageHessian<D> hessianPrewitt(Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		if( derivType == GrayF32.class )
			return (ImageHessian<D>)hessian(GradientPrewitt.class,GrayF32.class, GIO, FIB);
		else if( derivType == GrayS16.class )
			return (ImageHessian<D>)hessian(GradientPrewitt.class,GrayS16.class, GIO, FIB);
		else
			throw new IllegalArgumentException("Not supported yet");
	}

	public <D extends ImageGray> ImageHessian<D> hessianThree(Class<D> derivType, GeneralizedImageOps GIO, FactoryImageBorder FIB) {
		if( derivType == GrayF32.class )
			return (ImageHessian<D>)hessian(GradientThree.class,GrayF32.class, GIO, FIB);
		else if( derivType == GrayS16.class )
			return (ImageHessian<D>)hessian(GradientThree.class,GrayS16.class, GIO, FIB);
		else
			throw new IllegalArgumentException("Not supported yet");
	}

	private static Method findReduce( String name , Class<?> inputType , Class<?> derivType  ) {
		Method m;
		try {
			m = GradientReduceToSingle.class.getDeclaredMethod(name, inputType,inputType,derivType,derivType);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method findDerivative(Class<?> derivativeClass,
										 Class<?> inputType , Class<?> derivType, GeneralizedImageOps GIO) {
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(inputType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			Class<?> ISC = InputSanityCheck.class;
			Class<?> DHF = DerivativeHelperFunctions.class;
			Class<?> CINB = ConvolveImageNoBorder.class;
			Class <?> CJBG = ConvolveJustBorder_General.class;
			Class<?> GSO = GradientSobel_Outer.class;
			Class <?> GSUO = GradientSobel_UnrolledOuter.class;
			m = derivativeClass.getDeclaredMethod("process", inputType,derivType,derivType,borderType, ISC, DHF, CINB, CJBG, GSO, GSUO);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method findSobelDerivative(Class<?> derivativeClass,
										 Class<?> inputType , Class<?> derivType, GeneralizedImageOps GIO) {
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(inputType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			Class<?> ISC = InputSanityCheck.class;
			Class<?> DHF = DerivativeHelperFunctions.class;
			Class<?> CINB = ConvolveImageNoBorder.class;
			Class <?> CJBG = ConvolveJustBorder_General.class;
			Class<?> GSO = GradientSobel_Outer.class;
			Class <?> GSUO = GradientSobel_UnrolledOuter.class;
			m = derivativeClass.getDeclaredMethod("process", inputType,derivType,derivType,borderType, ISC, DHF, CINB, CJBG, GSO, GSUO);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method findThreeDerivative(Class<?> derivativeClass,
										 Class<?> inputType , Class<?> derivType, GeneralizedImageOps GIO) {
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(inputType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			Class<?> ISC = InputSanityCheck.class;
			Class<?> DHF = DerivativeHelperFunctions.class;
			Class<?> CINB = ConvolveImageNoBorder.class;
			Class <?> CJBG = ConvolveJustBorder_General.class;
			Class<?> GSO = GradientSobel_Outer.class;
			Class <?> GSUO = GradientSobel_UnrolledOuter.class;
			m = derivativeClass.getDeclaredMethod("process", inputType,derivType,derivType,borderType, ISC, DHF, CINB, CJBG, GSO, GSUO);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method findHessian(Class<?> derivativeClass,
									  Class<?> inputType , Class<?> derivType, GeneralizedImageOps GIO) {
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(inputType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			m = derivativeClass.getDeclaredMethod("process", inputType,derivType,derivType,derivType,borderType);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method findHessianFromGradient(Class<?> derivativeClass, Class<?> imageType, GeneralizedImageOps GIO ) {
		String name = derivativeClass.getSimpleName().substring(8);
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(imageType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			m = HessianFromGradient.class.getDeclaredMethod("hessian"+name, imageType,imageType,imageType,imageType,imageType,borderType);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}

	private static Method FindHessianFromGradient(Class<?> derivativeClass, Class<?> imageType, GeneralizedImageOps GIO ) {
		String name = derivativeClass.getSimpleName().substring(8);
		Method m;
		try {
			Class<?> borderType = GIO.isFloatingPoint(imageType) ? ImageBorder_F32.class : ImageBorder_S32.class;
			Class<?> ISC = InputSanityCheck.class;
			Class<?> DHF = DerivativeHelperFunctions.class;
			Class<?> CINB = ConvolveImageNoBorder.class;
			Class <?> CJBG = ConvolveJustBorder_General.class;
			Class<?> GSO = GradientSobel_Outer.class;
			Class <?> GSUO = GradientSobel_UnrolledOuter.class;
			m = HessianFromGradient.class.getDeclaredMethod("hessian"+name, imageType,imageType,imageType,imageType,imageType,borderType, ISC, DHF, CINB, CJBG, GSO, GSUO);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Input and derivative types are probably not compatible",e);
		}
		return m;
	}
}
