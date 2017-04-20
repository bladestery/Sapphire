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

package boofcv.alg.misc;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.blur.GBlurImageOps;
import boofcv.struct.image.*;
import sapphire.app.SapphireObject;

/**
 * Generalized version of {@link ImageStatistics}.  Type checking is performed at runtime instead of at compile type.
 *
 * @author Peter Abeles
 */
public class GImageStatistics implements SapphireObject {
	public GImageStatistics() {}
	/**
	 * Returns the absolute value of the element with the largest absolute value, across all bands
	 *
	 * @param input Input image. Not modified.
	 * @return Largest pixel absolute value.
	 */
	public double maxAbs( ImageBase input, ImageStatistics IS ) {
		if( input instanceof ImageGray) {
			if (GrayU8.class == input.getClass()) {
				return IS.maxAbs((GrayU8) input);
			} else if (GrayS8.class == input.getClass()) {
				return IS.maxAbs((GrayS8) input);
			} else if (GrayU16.class == input.getClass()) {
				return IS.maxAbs((GrayU16) input);
			} else if (GrayS16.class == input.getClass()) {
				return IS.maxAbs((GrayS16) input);
			} else if (GrayS32.class == input.getClass()) {
				return IS.maxAbs((GrayS32) input);
			} else if (GrayS64.class == input.getClass()) {
				return IS.maxAbs((GrayS64) input);
			} else if (GrayF32.class == input.getClass()) {
				return IS.maxAbs((GrayF32) input);
			} else if (GrayF64.class == input.getClass()) {
				return IS.maxAbs((GrayF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type: " + input.getClass().getSimpleName());
			}
		} else if( input instanceof ImageInterleaved ) {
			if (InterleavedU8.class == input.getClass()) {
				return IS.maxAbs((InterleavedU8) input);
			} else if (InterleavedS8.class == input.getClass()) {
				return IS.maxAbs((InterleavedS8) input);
			} else if (InterleavedU16.class == input.getClass()) {
				return IS.maxAbs((InterleavedU16) input);
			} else if (InterleavedS16.class == input.getClass()) {
				return IS.maxAbs((InterleavedS16) input);
			} else if (InterleavedS32.class == input.getClass()) {
				return IS.maxAbs((InterleavedS32) input);
			} else if (InterleavedS64.class == input.getClass()) {
				return IS.maxAbs((InterleavedS64) input);
			} else if (InterleavedF32.class == input.getClass()) {
				return IS.maxAbs((InterleavedF32) input);
			} else if (InterleavedF64.class == input.getClass()) {
				return IS.maxAbs((InterleavedF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type: " + input.getClass().getSimpleName());
			}
		} else {
			throw new IllegalArgumentException("Planar image support needs to be added");
		}
	}

	/**
	 * Returns the maximum pixel value across all bands.
	 *
	 * @param input Input image. Not modified.
	 * @return Maximum pixel value.
	 */
	public double max( ImageBase input, ImageStatistics IS ) {
		if( input instanceof ImageGray) {
			if (GrayU8.class == input.getClass()) {
				return IS.max((GrayU8) input);
			} else if (GrayS8.class == input.getClass()) {
				return IS.max((GrayS8) input);
			} else if (GrayU16.class == input.getClass()) {
				return IS.max((GrayU16) input);
			} else if (GrayS16.class == input.getClass()) {
				return IS.max((GrayS16) input);
			} else if (GrayS32.class == input.getClass()) {
				return IS.max((GrayS32) input);
			} else if (GrayS64.class == input.getClass()) {
				return IS.max((GrayS64) input);
			} else if (GrayF32.class == input.getClass()) {
				return IS.max((GrayF32) input);
			} else if (GrayF64.class == input.getClass()) {
				return IS.max((GrayF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type");
			}
		} else if( input instanceof ImageInterleaved ) {
			if (InterleavedU8.class == input.getClass()) {
				return IS.max((InterleavedU8) input);
			} else if (InterleavedS8.class == input.getClass()) {
				return IS.max((InterleavedS8) input);
			} else if (InterleavedU16.class == input.getClass()) {
				return IS.max((InterleavedU16) input);
			} else if (InterleavedS16.class == input.getClass()) {
				return IS.max((InterleavedS16) input);
			} else if (InterleavedS32.class == input.getClass()) {
				return IS.max((InterleavedS32) input);
			} else if (InterleavedS64.class == input.getClass()) {
				return IS.max((InterleavedS64) input);
			} else if (InterleavedF32.class == input.getClass()) {
				return IS.max((InterleavedF32) input);
			} else if (InterleavedF64.class == input.getClass()) {
				return IS.max((InterleavedF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type");
			}
		} else {
			throw new IllegalArgumentException("Planar image support needs to be added");
		}
	}

	/**
	 * Returns the minimum pixel value across all bands
	 *
	 * @param input Input image. Not modified.
	 * @return Minimum pixel value.
	 */
	public double min( ImageBase input , ImageStatistics IS) {
		if( input instanceof ImageGray) {
			if (GrayU8.class == input.getClass()) {
				return IS.min((GrayU8) input);
			} else if (GrayS8.class == input.getClass()) {
				return IS.min((GrayS8) input);
			} else if (GrayU16.class == input.getClass()) {
				return IS.min((GrayU16) input);
			} else if (GrayS16.class == input.getClass()) {
				return IS.min((GrayS16) input);
			} else if (GrayS32.class == input.getClass()) {
				return IS.min((GrayS32) input);
			} else if (GrayS64.class == input.getClass()) {
				return IS.min((GrayS64) input);
			} else if (GrayF32.class == input.getClass()) {
				return IS.min((GrayF32) input);
			} else if (GrayF64.class == input.getClass()) {
				return IS.min((GrayF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type: " + input.getClass().getSimpleName());
			}
		} else if( input instanceof ImageInterleaved ) {
			if (InterleavedU8.class == input.getClass()) {
				return IS.min((InterleavedU8) input);
			} else if (InterleavedS8.class == input.getClass()) {
				return IS.min((InterleavedS8) input);
			} else if (InterleavedU16.class == input.getClass()) {
				return IS.min((InterleavedU16) input);
			} else if (InterleavedS16.class == input.getClass()) {
				return IS.min((InterleavedS16) input);
			} else if (InterleavedS32.class == input.getClass()) {
				return IS.min((InterleavedS32) input);
			} else if (InterleavedS64.class == input.getClass()) {
				return IS.min((InterleavedS64) input);
			} else if (InterleavedF32.class == input.getClass()) {
				return IS.min((InterleavedF32) input);
			} else if (InterleavedF64.class == input.getClass()) {
				return IS.min((InterleavedF64) input);
			} else {
				throw new IllegalArgumentException("Unknown Image Type: " + input.getClass().getSimpleName());
			}
		} else {
			throw new IllegalArgumentException("Planar image support needs to be added");
		}
	}

	/**
	 * <p>
	 * Returns the sum of all the pixels in the image across all bands.
	 * </p>
	 *
	 * @param input Input image. Not modified.
	 * @return Sum of pixel intensity values
	 */
	public double sum( ImageBase input , ImageStatistics IS) {

		if( input instanceof ImageGray) {
			if (GrayU8.class == input.getClass()) {
				return IS.sum((GrayU8) input);
			} else if (GrayS8.class == input.getClass()) {
				return IS.sum((GrayS8) input);
			} else if (GrayU16.class == input.getClass()) {
				return IS.sum((GrayU16) input);
			} else if (GrayS16.class == input.getClass()) {
				return IS.sum((GrayS16) input);
			} else if (GrayS32.class == input.getClass()) {
				return IS.sum((GrayS32) input);
			} else if (GrayS64.class == input.getClass()) {
				return IS.sum((GrayS64) input);
			} else if (GrayF32.class == input.getClass()) {
				return IS.sum((GrayF32) input);
			} else if (GrayF64.class == input.getClass()) {
				return IS.sum((GrayF64) input);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else if( input instanceof ImageInterleaved ) {
			if (InterleavedU8.class == input.getClass()) {
				return IS.sum((InterleavedU8) input);
			} else if (InterleavedS8.class == input.getClass()) {
				return IS.sum((InterleavedS8) input);
			} else if (InterleavedU16.class == input.getClass()) {
				return IS.sum((InterleavedU16) input);
			} else if (InterleavedS16.class == input.getClass()) {
				return IS.sum((InterleavedS16) input);
			} else if (InterleavedS32.class == input.getClass()) {
				return IS.sum((InterleavedS32) input);
			} else if (InterleavedS64.class == input.getClass()) {
				return IS.sum((InterleavedS64) input);
			} else if (InterleavedF32.class == input.getClass()) {
				return IS.sum((InterleavedF32) input);
			} else if (InterleavedF64.class == input.getClass()) {
				return IS.sum((InterleavedF64) input);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else if( input instanceof Planar ) {
			double sum = 0;
			Planar in = (Planar) input;
			for (int i = 0; i < in.getNumBands(); i++) {
				sum += sum( in.getBand(i),IS);
			}
			return sum;
		} else {
			throw new IllegalArgumentException("Planar image support needs to be added");
		}
	}

	/**
	 * Returns the mean pixel intensity value.
	 *histogram
	 * @param input Input image. Not modified.
	 * @return Mean pixel value
	 */
	public double mean( ImageBase input, ImageStatistics IS) {

		if( input instanceof ImageGray) {
			if (GrayU8.class == input.getClass()) {
				return IS.mean((GrayU8) input);
			} else if (GrayS8.class == input.getClass()) {
				return IS.mean((GrayS8) input);
			} else if (GrayU16.class == input.getClass()) {
				return IS.mean((GrayU16) input);
			} else if (GrayS16.class == input.getClass()) {
				return IS.mean((GrayS16) input);
			} else if (GrayS32.class == input.getClass()) {
				return IS.mean((GrayS32) input);
			} else if (GrayS64.class == input.getClass()) {
				return IS.mean((GrayS64) input);
			} else if (GrayF32.class == input.getClass()) {
				return IS.mean((GrayF32) input);
			} else if (GrayF64.class == input.getClass()) {
				return IS.mean((GrayF64) input);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else if( input instanceof ImageInterleaved ) {
			if (InterleavedU8.class == input.getClass()) {
				return IS.mean((InterleavedU8) input);
			} else if (InterleavedS8.class == input.getClass()) {
				return IS.mean((InterleavedS8) input);
			} else if (InterleavedU16.class == input.getClass()) {
				return IS.mean((InterleavedU16) input);
			} else if (InterleavedS16.class == input.getClass()) {
				return IS.mean((InterleavedS16) input);
			} else if (InterleavedS32.class == input.getClass()) {
				return IS.mean((InterleavedS32) input);
			} else if (InterleavedS64.class == input.getClass()) {
				return IS.mean((InterleavedS64) input);
			} else if (InterleavedF32.class == input.getClass()) {
				return IS.mean((InterleavedF32) input);
			} else if (InterleavedF64.class == input.getClass()) {
				return IS.mean((InterleavedF64) input);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else {
			throw new IllegalArgumentException("Planar image support needs to be added");
		}
	}

	/**
	 * Computes the variance of pixel intensity values inside the image.
	 *
	 * @param input Input image. Not modified.
	 * @param mean Mean pixel intensity value.
	 * @return Pixel variance
	 */
	public <T extends ImageGray> double variance(T input , double mean, ImageStatistics IS) {

		if( GrayU8.class == input.getClass() ) {
			return IS.variance((GrayU8)input,mean);
		} else if( GrayS8.class == input.getClass() ) {
			return IS.variance((GrayS8)input,mean);
		} else if( GrayU16.class == input.getClass() ) {
			return IS.variance((GrayU16)input,mean);
		} else if( GrayS16.class == input.getClass() ) {
			return IS.variance((GrayS16)input,mean);
		} else if( GrayS32.class == input.getClass() ) {
			return IS.variance((GrayS32)input,mean);
		} else if( GrayS64.class == input.getClass() ) {
			return IS.variance((GrayS64)input,mean);
		} else if( GrayF32.class == input.getClass() ) {
			return IS.variance((GrayF32)input,mean);
		} else if( GrayF64.class == input.getClass() ) {
			return IS.variance((GrayF64)input,mean);
		} else {
			throw new IllegalArgumentException("Unknown image Type");
		}
	}

	/**
	 * Computes the mean of the difference squared between the two images.
	 *
	 * @param inputA Input image. Not modified.
	 * @param inputB Input image. Not modified.
	 * @return Mean difference squared
	 */
	public <T extends ImageBase> double meanDiffSq( T inputA , T inputB, ImageStatistics IS, InputSanityCheck ISC) {

		if( inputA instanceof ImageGray) {
			if (GrayU8.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayU8) inputA, (GrayU8) inputB, ISC);
			} else if (GrayS8.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayS8) inputA, (GrayS8) inputB, ISC);
			} else if (GrayU16.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayU16) inputA, (GrayU16) inputB, ISC);
			} else if (GrayS16.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayS16) inputA, (GrayS16) inputB, ISC);
			} else if (GrayS32.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayS32) inputA, (GrayS32) inputB, ISC);
			} else if (GrayS64.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayS64) inputA, (GrayS64) inputB, ISC);
			} else if (GrayF32.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayF32) inputA, (GrayF32) inputB, ISC);
			} else if (GrayF64.class == inputA.getClass()) {
				return IS.meanDiffSq((GrayF64) inputA, (GrayF64) inputB, ISC);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else if( inputA instanceof ImageInterleaved ) {
			if (InterleavedU8.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedU8) inputA, (InterleavedU8) inputB, ISC);
			} else if (InterleavedS8.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedS8) inputA, (InterleavedS8) inputB, ISC);
			} else if (InterleavedU16.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedU16) inputA, (InterleavedU16) inputB, ISC);
			} else if (InterleavedS16.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedS16) inputA, (InterleavedS16) inputB, ISC);
			} else if (InterleavedS32.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedS32) inputA, (InterleavedS32) inputB, ISC);
			} else if (InterleavedS64.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedS64) inputA, (InterleavedS64) inputB, ISC);
			} else if (InterleavedF32.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedF32) inputA, (InterleavedF32) inputB, ISC);
			} else if (InterleavedF64.class == inputA.getClass()) {
				return IS.meanDiffSq((InterleavedF64) inputA, (InterleavedF64) inputB, ISC);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else {
			throw new IllegalArgumentException("Planar images needs to be added");
		}
	}

	/**
	 * Computes the mean of the absolute value of the difference between the two images across all bands
	 *
	 * @param inputA Input image. Not modified.
	 * @param inputB Input image. Not modified.
	 * @return Mean absolute difference
	 */
	public <T extends ImageBase> double meanDiffAbs( T inputA , T inputB, ImageStatistics IS, InputSanityCheck ISC) {

		if( inputA instanceof ImageGray) {
			if (GrayU8.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayU8) inputA, (GrayU8) inputB, ISC);
			} else if (GrayS8.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayS8) inputA, (GrayS8) inputB, ISC);
			} else if (GrayU16.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayU16) inputA, (GrayU16) inputB, ISC);
			} else if (GrayS16.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayS16) inputA, (GrayS16) inputB, ISC);
			} else if (GrayS32.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayS32) inputA, (GrayS32) inputB, ISC);
			} else if (GrayS64.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayS64) inputA, (GrayS64) inputB, ISC);
			} else if (GrayF32.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayF32) inputA, (GrayF32) inputB, ISC);
			} else if (GrayF64.class == inputA.getClass()) {
				return IS.meanDiffAbs((GrayF64) inputA, (GrayF64) inputB, ISC);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else if( inputA instanceof ImageInterleaved ) {
			if (InterleavedU8.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedU8) inputA, (InterleavedU8) inputB, ISC);
			} else if (InterleavedS8.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedS8) inputA, (InterleavedS8) inputB, ISC);
			} else if (InterleavedU16.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedU16) inputA, (InterleavedU16) inputB, ISC);
			} else if (InterleavedS16.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedS16) inputA, (InterleavedS16) inputB, ISC);
			} else if (InterleavedS32.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedS32) inputA, (InterleavedS32) inputB, ISC);
			} else if (InterleavedS64.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedS64) inputA, (InterleavedS64) inputB, ISC);
			} else if (InterleavedF32.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedF32) inputA, (InterleavedF32) inputB, ISC);
			} else if (InterleavedF64.class == inputA.getClass()) {
				return IS.meanDiffAbs((InterleavedF64) inputA, (InterleavedF64) inputB, ISC);
			} else {
				throw new IllegalArgumentException("Unknown image Type");
			}
		} else {
			throw new IllegalArgumentException("Planar images needs to be added");
		}
	}

	/**
	 * Computes the histogram of intensity values for the image.  For floating point images it is rounded
	 * to the nearest integer using "(int)value".
	 *
	 * @param input (input) Image.
	 * @param minValue (input) Minimum possible intensity value   Ignored for unsigned images.
	 * @param histogram (output) Storage for histogram. Number of elements must be equal to max value.
	 */
	public void histogram(ImageGray input , int minValue , int histogram[], ImageStatistics IS) {
		if( GrayU8.class == input.getClass() ) {
			IS.histogram((GrayU8)input,histogram);
		} else if( GrayS8.class == input.getClass() ) {
			IS.histogram((GrayS8)input,minValue,histogram);
		} else if( GrayU16.class == input.getClass() ) {
			IS.histogram((GrayU16)input,histogram);
		} else if( GrayS16.class == input.getClass() ) {
			IS.histogram((GrayS16)input,minValue,histogram);
		} else if( GrayS32.class == input.getClass() ) {
			IS.histogram((GrayS32)input,minValue,histogram);
		} else if( GrayS64.class == input.getClass() ) {
			IS.histogram((GrayS64)input,minValue,histogram);
		} else if( GrayF32.class == input.getClass() ) {
			IS.histogram((GrayF32)input,minValue,histogram);
		} else if( GrayF64.class == input.getClass() ) {
			IS.histogram((GrayF64)input,minValue,histogram);
		} else {
			throw new IllegalArgumentException("Unknown image Type");
		}
	}
}
