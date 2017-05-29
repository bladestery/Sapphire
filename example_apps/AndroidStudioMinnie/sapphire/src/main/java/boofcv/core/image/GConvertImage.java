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

package boofcv.core.image;

import android.renderscript.ScriptGroup;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.struct.image.*;

import java.lang.reflect.Method;

/**
 * <p>
 * Generalized functions for converting between different image types. Numerical values do not change or are closely
 * approximated in these functions. If an output image is not specified then a new instance is declared and returned.
 * </p>
 *
 * @author Peter Abeles
 */
@SuppressWarnings("unchecked")
public class GConvertImage {
	/**
	 * <p>
	 * Converts one type of between two types of images using a default method.  Both are the same image type
	 * then a simple type cast if performed at the pixel level.  If the input is multi-band and the output
	 * is single band then it will average the bands.  If input is single band and output is multi-band
	 * then the single band is copied into each of the other bands.
	 * </p>
	 *
	 * <p>
	 * In some cases a temporary image will be created to store intermediate results.  If this is an issue
	 * you will need to create a specialized conversion algorithm.
	 * </p>
	 *
	 * @param input Input image which is being converted. Not modified.
	 * @param output (Optional) The output image.  If null a new image is created. Modified.
	 * @return Converted image.
	 */
	public static void convert(ImageBase input , ImageBase output , InputSanityCheck ISC, GeneralizedImageOps GIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI, ImageType IT) {

		if( input instanceof ImageGray) {
			ImageGray sb = (ImageGray)input;
			if( output instanceof ImageGray) {
				if (input.getClass() == output.getClass()) {
					output.setTo(input);
				} else {
					try {
						Method m = ConvertImage.class.getMethod("convert", input.getClass(), output.getClass(), InputSanityCheck.class);
						//System.out.println(input.getClass());
						//System.out.println(output.getClass());
						System.out.println(m);
						m.invoke(CI, input, output, ISC);
					} catch (Exception e) {
						throw new IllegalArgumentException("Unknown conversion");
					}
				}
			} else if( output instanceof Planar) {
				Planar ms = (Planar)output;
				for (int i = 0; i < ms.getNumBands(); i++) {
					convert(input,ms.getBand(i), ISC, GIO, GIMO, IMO, CI, IT);
				}
			} else if( output instanceof ImageInterleaved ) {
				ImageInterleaved il = (ImageInterleaved)output;
				for (int i = 0; i < il.getNumBands(); i++) {
					GIMO.insertBand(sb, i, il, IMO);
				}
			}
		} else if( input instanceof ImageInterleaved && output instanceof ImageInterleaved )  {
			if( input.getClass() == output.getClass() ) {
				output.setTo(input);
			} else {
				try {
					Method m = ConvertImage.class.getMethod("convert", input.getClass(), output.getClass(), InputSanityCheck.class);
					m.invoke(CI, input, output, ISC);
				} catch (Exception e) {
					throw new IllegalArgumentException("Unknown conversion");
				}
			}
		} else if( input instanceof Planar && output instanceof ImageGray)  {
			Planar mi = (Planar)input;
			ImageGray so = (ImageGray)output;

			if( mi.getImageType().getDataType() != so.getDataType() ) {
				int w = output.width;
				int h = output.height;
				ImageGray tmp = GIO.createSingleBand(mi.getImageType().getDataType(),w,h, IT);
				average(mi,tmp, ISC, CI);
				convert(tmp,so, ISC, GIO, GIMO, IMO, CI, IT);
			} else {
				average(mi,so, ISC, CI);
			}
		} else if( input instanceof Planar && output instanceof ImageInterleaved )  {
			try {
				Method m = ConvertImage.class.getMethod("convert", input.getClass(), output.getClass(), InputSanityCheck.class);
				m.invoke(CI, input, output, ISC);
			} catch (Exception e) {
				throw new IllegalArgumentException("Unknown conversion");
			}
		} else if( input instanceof Planar && output instanceof Planar) {
			Planar mi = (Planar) input;
			Planar mo = (Planar) output;

			if (mi.getBandType() == mo.getBandType()) {
				mo.setTo(mi);
			} else {
				for (int i = 0; i < mi.getNumBands(); i++) {
					convert(mi.getBand(i), mo.getBand(i), ISC, GIO, GIMO, IMO, CI, IT);
				}
			}
		} else if( input instanceof ImageInterleaved && output instanceof Planar)  {
			try {
				Method m = ConvertImage.class.getMethod("convert", input.getClass(), output.getClass(), InputSanityCheck.class);
				m.invoke(CI, input, output, ISC);
			} catch (Exception e) {
				throw new IllegalArgumentException("Unknown conversion");
			}
		} else if( input instanceof ImageInterleaved && output instanceof ImageGray)  {
			ImageInterleaved mb = (ImageInterleaved)input;
			ImageGray so = (ImageGray)output;

			if( mb.getImageType().getDataType() != so.getDataType() ) {
				int w = output.width;
				int h = output.height;
				ImageGray tmp = GIO.createSingleBand(mb.getImageType().getDataType(),w,h, IT);
				average(mb,tmp, ISC, CI);
				convert(tmp,so, ISC, GIO, GIMO, IMO, CI, IT);
			} else {
				average(mb,so, ISC, CI);
			}
		} else {
			String nameInput = input.getClass().getSimpleName();
			String nameOutput = output.getClass().getSimpleName();
			throw new IllegalArgumentException("Don't know how to convert between input types. "+nameInput+" "+nameOutput);
		}
	}

	/**
	 * Converts a {@link Planar} into a {@link ImageGray} by computing the average value of each pixel
	 * across all the bands.
	 *
	 * @param input Input Planar image that is being converted. Not modified.
	 * @param output (Optional) The single band output image.  If null a new image is created. Modified.
	 * @return Converted image.
	 */
	public static <T extends ImageGray>T average(Planar<T> input , T output, InputSanityCheck ISC, ConvertImage CI ) {
		Class type = input.getBandType();
		if( type == GrayU8.class ) {
			return (T)CI.average((Planar<GrayU8>)input,(GrayU8)output, ISC);
		} else if( type == GrayS8.class ) {
			return (T)CI.average((Planar<GrayS8>)input,(GrayS8)output, ISC);
		} else if( type == GrayU16.class ) {
			return (T)CI.average((Planar<GrayU16>)input,(GrayU16)output, ISC);
		} else if( type == GrayS16.class ) {
			return (T)CI.average((Planar<GrayS16>)input,(GrayS16)output, ISC);
		} else if( type == GrayS32.class ) {
			return (T)CI.average((Planar<GrayS32>)input,(GrayS32)output, ISC);
		} else if( type == GrayS64.class ) {
			return (T)CI.average((Planar<GrayS64>)input,(GrayS64)output, ISC);
		} else if( type == GrayF32.class ) {
			return (T)CI.average((Planar<GrayF32>)input,(GrayF32)output, ISC);
		} else if( type == GrayF64.class ) {
			return (T)CI.average((Planar<GrayF64>)input,(GrayF64)output, ISC);
		} else {
			throw new IllegalArgumentException("Unknown image type: "+type.getSimpleName());
		}
	}

	/**
	 * Converts a {@link Planar} into a {@link ImageGray} by computing the average value of each pixel
	 * across all the bands.
	 *
	 * @param input Input Planar image that is being converted. Not modified.
	 * @param output (Optional) The single band output image.  If null a new image is created. Modified.
	 * @return Converted image.
	 */
	public static <T extends ImageGray>T average(ImageInterleaved input , T output, InputSanityCheck ISC, ConvertImage CI) {
		ImageDataType type = input.getImageType().getDataType();
		if( type == ImageDataType.U8) {
			return (T)CI.average((InterleavedU8)input,(GrayU8)output, ISC);
		} else if( type == ImageDataType.S8) {
			return (T)CI.average((InterleavedS8)input,(GrayS8)output, ISC);
		} else if( type == ImageDataType.U16 ) {
			return (T)CI.average((InterleavedU16)input,(GrayU16)output, ISC);
		} else if( type == ImageDataType.S16 ) {
			return (T)CI.average((InterleavedS16)input,(GrayS16)output, ISC);
		} else if( type == ImageDataType.S32 ) {
			return (T)CI.average((InterleavedS32)input,(GrayS32)output, ISC);
		} else if( type == ImageDataType.S64 ) {
			return (T)CI.average((InterleavedS64)input,(GrayS64)output, ISC);
		} else if( type == ImageDataType.F32 ) {
			return (T)CI.average((InterleavedF32)input,(GrayF32)output, ISC);
		} else if( type == ImageDataType.F64 ) {
			return (T)CI.average((InterleavedF64)input,(GrayF64)output, ISC);
		} else {
			throw new IllegalArgumentException("Unknown image type: " + type);
		}
	}

	/**
	 * Converts pixel values in the input image into an integer values from 0 to numValues.
	 * @param input Input image
	 * @param min minimum input pixel value, inclusive
	 * @param max maximum input pixel value, inclusive
	 * @param numValues Number of possible pixel values in output image
	 * @param output (Optional) Storage for the output image.  Can be null.
	 * @return The converted output image.
	 */
	public static GrayU8 convert(ImageGray input , double min , double max , int numValues , GrayU8 output , InputSanityCheck ISC, GeneralizedImageOps GIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI, ImageType IT)
	{
		// see if it can use the faster straight forward convert
		if( min == 0 && max == 255 && numValues == 256 ) {
			if( output == null )
				output = new GrayU8(input.width,input.height);
			convert(input,output, ISC, GIO, GIMO, IMO, CI, IT);
			return output;
		}

		ImageDataType type = input.getImageType().getDataType();
		if( type == ImageDataType.U8) {
			return CI.convert((GrayU8)input,(int)min,(int)max,numValues,output, ISC);
		} else if( type == ImageDataType.S8) {
			return CI.convert((GrayS8)input,(int)min,(int)max,numValues,output, ISC);
		} else if( type == ImageDataType.U16 ) {
			return CI.convert((GrayU16)input,(int)min,(int)max,numValues,output, ISC);
		} else if( type == ImageDataType.S16 ) {
			return CI.convert((GrayS16)input,(int)min,(int)max,numValues,output, ISC);
		} else if( type == ImageDataType.S32 ) {
			return CI.convert((GrayS32)input,(int)min,(int)max,numValues,output, ISC);
		} else if( type == ImageDataType.S64 ) {
			return CI.convert((GrayS64)input,(long)min,(long)max,numValues,output, ISC);
		} else if( type == ImageDataType.F32 ) {
			return CI.convert((GrayF32)input,(float)min,(float)max,numValues,output, ISC);
		} else if( type == ImageDataType.F64 ) {
			return CI.convert((GrayF64)input,min,max,numValues,output, ISC);
		} else {
			throw new IllegalArgumentException("Unknown image type: " + type);
		}
	}
}
