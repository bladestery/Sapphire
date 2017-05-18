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

package boofcv.alg.filter.binary;

import boofcv.abst.filter.binary.LocalSquareBlockMinMaxBinaryFilter;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.impl.ThresholdSauvola;
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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.*;
import sapphire.app.SapphireObject;


/**
 * Weakly typed version of {@link ThresholdImageOps}.
 *
 * @author Peter Abeles
 */
public class GThresholdImageOps implements SapphireObject {
	private static ImageType IT;
	private static GImageStatistics GIS;
	private static GeneralizedImageOps GIO;
	private static GImageMiscOps GIMO;
	private static InputSanityCheck ISC;
	private static ImageMiscOps IMO;
	
	public GThresholdImageOps() {}

	/**
	 * <p>
	 * Computes the variance based threshold using Otsu's method from an input image. Internally it uses
	 * {@link #computeOtsu(int[], int, int)} and {@link boofcv.alg.misc.GImageStatistics#histogram(ImageGray, int, int[])}
	 * </p>
	 *
	 * @param input Input gray-scale image
	 * @param minValue The minimum value of a pixel in the image.  (inclusive)
	 * @param maxValue The maximum value of a pixel in the image.  (inclusive)
	 * @return Selected threshold.
	 */
	public int computeOtsu(ImageGray input , int minValue , int maxValue, GImageStatistics GIS, ImageStatistics IS) {

		int range = 1+maxValue - minValue;
		int histogram[] = new int[ range ];

		GIS.histogram(input,minValue,histogram, IS);

		// Total number of pixels
		int total = input.width*input.height;

		return computeOtsu(histogram,range,total)+minValue;
	}

	/**
	 * Computes the variance based Otsu threshold from a histogram directly. The threshold is selected by minimizing the
	 * spread of both foreground and background pixel values.
	 *
	 * @param histogram Histogram of pixel intensities.
	 * @param length Number of elements in the histogram.
	 * @param totalPixels Total pixels in the image
	 * @return Selected threshold
	 */
	// original code from http://www.labbookpages.co.uk/software/imgProc/otsuThreshold.html
	//                    Dr. Andrew Greensted
	// modifications to reduce overflow
	public int computeOtsu( int histogram[] , int length , int totalPixels ) {

		double dlength = length;
		double sum = 0;
		for (int i=0 ; i< length ; i++)
			sum += (i/dlength)*histogram[i];


		double sumB = 0;
		int wB = 0;

		double varMax = 0;
		int threshold = 0;

		for (int i=0 ; i<length ; i++) {
			wB += histogram[i];               // Weight Background
			if (wB == 0) continue;

			int wF = totalPixels - wB;         // Weight Foreground
			if (wF == 0) break;

			sumB += (i/dlength)*histogram[i];

			double mB = sumB / wB;            // Mean Background
			double mF = (sum - sumB) / wF;    // Mean Foreground

			// Calculate Between Class Variance
			double varBetween = (double)wB*(double)wF*(mB - mF)*(mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = i;
			}
		}

		return threshold;
	}

	/**
	 * <p>
	 * Computes a threshold which maximizes the entropy between the foreground and background regions.  See
	 * {@link #computeEntropy(int[], int, int)} for more details.
	 * </p>
	 *
	 * @see boofcv.alg.misc.GImageStatistics#histogram(ImageGray, int, int[])
	 *
	 * @param input Input gray-scale image
	 * @param minValue The minimum value of a pixel in the image.  (inclusive)
	 * @param maxValue The maximum value of a pixel in the image.  (inclusive)
	 * @return Selected threshold.
	 */
	public int computeEntropy(ImageGray input , int minValue , int maxValue, ImageStatistics IS ) {

		int range = 1 + maxValue - minValue;
		int histogram[] = new int[ range ];

		GIS.histogram(input,minValue,histogram, IS);

		// Total number of pixels
		int total = input.width*input.height;

		return computeEntropy(histogram, range, total)+minValue;
	}

	/**
	 * <p>
	 * Computes a threshold which maximizes the entropy between the foreground and background regions.  See [1]
	 * for algorithmic details, which cites [2].
	 * </p>
	 *
	 * <p>
	 * [1] E.R. Davies "Machine Vision Theory Algorithms Practicalities" 3rd Ed. 2005. pg. 124<br>
	 * [2] Hannah, Ian, Devesh Patel, and Roy Davies. "The use of variance and entropic thresholding methods
	 * for image segmentation." Pattern Recognition 28.8 (1995): 1135-1143.
	 * </p>
	 *
	 * @param histogram Histogram of pixel intensities.
	 * @param length Number of elements in the histogram.
	 * @param totalPixels Total pixels in the image
	 * @return Selected threshold
	 */
	public int computeEntropy( int histogram[] , int length , int totalPixels ) {

		// precompute p[i]*ln(p[i]) and handle special case where p[i] = 0
		double p[] = new double[length];
		for (int i = 0; i < length; i++) {
			int h = histogram[i];
			if( h == 0 ) {
				p[i] = 0;
			} else {
				p[i] = h/(double)totalPixels;
				p[i] *= Math.log(p[i]);
			}
		}

		double bestScore = 0;
		int bestIndex = 0;
		int countF = 0;

		for (int i=0 ; i<length ; i++) {
			countF += histogram[i];
			double sumF = countF/(double)totalPixels;

			if( sumF == 0 || sumF == 1.0 ) continue;

			double sumB = 1.0-sumF;

			double HA = 0;
			for (int j = 0; j <= i; j++) {
				HA += p[j];
			}
			HA/=sumF;

			double HB = 0;
			for (int j = i+1; j < length; j++) {
				HB += p[j];
			}
			HB/=sumB;

			double entropy = Math.log(sumF) + Math.log(sumB)  - HA - HB;

			if( entropy > bestScore ) {
				bestScore = entropy;
				bestIndex = i;
			}
		}

		return bestIndex;
	}

	/**
	 * Applies a global threshold across the whole image.  If 'down' is true, then pixels with values &le;
	 * to 'threshold' are set to 1 and the others set to 0.  If 'down' is false, then pixels with values >
	 * to 'threshold' are set to 1 and the others set to 0.
	 *
	 * @param input Input image. Not modified.
	 * @param output (Optional) Binary output image. If null a new image will be declared. Modified.
	 * @param threshold threshold value.
	 * @param down If true then the inequality &le; is used, otherwise if false then &gt; is used.
	 * @return binary image.
	 */
	public <T extends ImageGray>
	GrayU8 threshold(T input , GrayU8 output ,
					 double threshold , boolean down, ThresholdImageOps TIO, InputSanityCheck ISC, GeneralizedImageOps GIO)
	{
		if( input instanceof GrayF32) {
			return TIO.threshold((GrayF32)input,output,(float)threshold,down, ISC, GIO);
		} else if( input instanceof GrayU8) {
			return TIO.threshold((GrayU8)input,output,(int)threshold,down, ISC, GIO);
		} else if( input instanceof GrayU16) {
			return TIO.threshold((GrayU16)input,output,(int)threshold,down, ISC, GIO);
		} else if( input instanceof GrayS16) {
			return TIO.threshold((GrayS16)input,output,(int)threshold,down, ISC, GIO);
		} else if( input instanceof GrayS32) {
			return TIO.threshold((GrayS32)input,output,(int)threshold,down, ISC, GIO);
		} else if( input instanceof GrayF64) {
			return TIO.threshold((GrayF64)input,output,threshold,down, ISC, GIO);
		} else {
			throw new IllegalArgumentException("Unknown image type: "+input.getClass().getSimpleName());
		}
	}

	/**
	 * <p>
	 * Thresholds the image using a locally adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the average value of the surrounding pixels times the scale.
	 * If down is true then b(x,y) = I(x,y) &le; T(x,y) * scale ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) &gt; T(x,y) * scale ? 0 : 1
	 * </p>
	 *
	 * <p>
	 * NOTE: Internally, images are declared to store intermediate results.  If more control is needed over memory
	 * call the type specific function.
	 * </p>
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param scale Scale factor used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param work1 (Optional) Internal workspace.  Can be null
	 * @param work2 (Optional) Internal workspace.  Can be null
	 * @return binary image.
	 */
	public <T extends ImageGray>
	GrayU8 localSquare(T input, GrayU8 output,
					   int radius, double scale, boolean down, T work1, T work2, ThresholdImageOps TIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
					   ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplConvolveMean ICM)
	{
		if( input instanceof GrayF32) {
			return TIO.localSquare((GrayF32) input, output, radius, (float) scale, down,
					(GrayF32) work1, (GrayF32) work2, ISC, GIO, BIO, CIM, CN, CNN, CINB, CNJB, ICM);
		} else if( input instanceof GrayU8) {
			return TIO.localSquare((GrayU8) input, output, radius, (float) scale, down,
					(GrayU8) work1, (GrayU8) work2, ISC, GIO, BIO, CIM, CN, CNN, CINB, CNJB, ICM);
		} else {
			throw new IllegalArgumentException("Unknown image type: "+input.getClass().getSimpleName());
		}
	}

	/**
	 * <p>
	 * Thresholds the image using a locally adaptive threshold that is computed using a local square region centered
	 * on each pixel.  The threshold is equal to the gaussian weighted sum of the surrounding pixels times the scale.
	 * If down is true then b(x,y) = I(x,y) &le; T(x,y) * scale ? 1 : 0.  Otherwise
	 * b(x,y) = I(x,y) &gt; T(x,y) * scale ? 0 : 1
	 * </p>
	 *
	 * <p>
	 * NOTE: Internally, images are declared to store intermediate results.  If more control is needed over memory
	 * call the type specific function.
	 * </p>
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param scale Scale factor used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param work1 (Optional) Internal workspace.  Can be null
	 * @param work2 (Optional) Internal workspace.  Can be null
	 * @return binary image.
	 */
	public <T extends ImageGray>
	GrayU8 localGaussian(T input, GrayU8 output,
						 int radius, double scale, boolean down,
						 T work1, ImageGray work2, ThresholdImageOps TIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
						 FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB)
	{
		if( input instanceof GrayF32) {
			return TIO.localGaussian((GrayF32) input, output, radius, (float) scale, down,
					(GrayF32) work1, (GrayF32) work2, ISC, GIO, BIO, FKG, CN, CNN, CINB, CNJB);
		} else if( input instanceof GrayU8) {
			return TIO.localGaussian((GrayU8) input, output, radius, (float) scale, down,
					(GrayU8) work1, (GrayU8) work2, ISC, GIO, BIO, FKG, CN, CNN, CINB, CNJB);
		} else {
			throw new IllegalArgumentException("Unknown image type: "+input.getClass().getSimpleName());
		}
	}

	/**
	 * Applies {@link boofcv.alg.filter.binary.impl.ThresholdSauvola Sauvola} thresholding to the input image.
	 * Intended for use with text image.
	 *
	 * @see boofcv.alg.filter.binary.impl.ThresholdSauvola
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of local region.  Try 15
	 * @param k Positive parameter used to tune threshold.  Try 0.3
	 * @param down Should it threshold up or down.
	 * @return binary image
	 */
	public <T extends ImageGray>
	GrayU8 localSauvola(T input, GrayU8 output, int radius, float k, boolean down)
	{
		ThresholdSauvola alg = new ThresholdSauvola(radius,k, down);

		if( output == null )
			output = new GrayU8(input.width,input.height);

		if( input instanceof GrayF32) {
			alg.process((GrayF32)input,output);
		} else {
			GrayF32 conv = new GrayF32(input.width,input.height);
			GConvertImage.convert(input, conv, ISC, GIO, GIMO, IMO);
			alg.process(conv,output);
		}

		return output;
	}

	/**
	 * Applies a threshold to an image by computing the min and max values in a regular grid across
	 * the input image.  See {@link ThresholdSquareBlockMinMax} for the details.
	 *
	 * @param input Input image.
	 * @param output (optional) Output binary image.  If null it will be declared internally.
	 * @param radius Radius of square region.
	 * @param scale Scale factor used to adjust threshold
	 * @param down Should it threshold up or down.
	 * @param textureThreshold If the min and max values are within this threshold the pixel will be set to 1.
	 * @return Binary image
	 */
	public <T extends ImageGray>
	GrayU8 localBlockMinMax(T input, GrayU8 output, int radius, double scale , boolean down, double textureThreshold, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO,
							ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB,
							ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
							GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG)
	{
		LocalSquareBlockMinMaxBinaryFilter<T> alg = new LocalSquareBlockMinMaxBinaryFilter<>(textureThreshold, radius * 2 + 1, scale, down,
				(Class<T>) input.getClass(), IT);

		if( output == null )
			output = new GrayU8(input.width,input.height);

		alg.process(input,output, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG);

		return output;
	}

}
