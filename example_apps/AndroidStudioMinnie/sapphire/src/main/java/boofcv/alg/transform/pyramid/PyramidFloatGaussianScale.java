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

import boofcv.abst.filter.blur.BlurStorageFilter;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.distort.DistortImageOps;
import boofcv.alg.distort.PixelTransformAffine_F32;
import boofcv.alg.distort.impl.DistortSupport;
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
import boofcv.alg.interpolate.InterpolatePixelS;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.ImageGray;
import boofcv.struct.pyramid.PyramidFloat;


/**
 * <p>
 * {@link PyramidFloat} in which each layer is constructed by 1) applying Gaussian blur to the previous layer, and then
 * 2) re-sampling the blurred previous layer. The scaling factor between each level are floating point number.
 * Unlike {@link PyramidDiscreteSampleBlur } the scale factors can be arbitrary and are not limited to certain integer
 * values.  The specified sigmas are the sigmas which are applied to each layer.
 * </p>
 *
 * <p>
 * NOTE: This can be considered the theoretically correct way to construct an image pyramid
 * with no sacrifices to improve speed.
 * </p>
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class PyramidFloatGaussianScale< T extends ImageGray> extends PyramidFloat<T> {
	// interpolation algorithm
	protected InterpolatePixelS<T> interpolate;

	// used to store the blurred image
	protected T tempImage;

	// how much each layer is blurred before sub-sampling
	protected float[] sigmaLayers;

	// The effective amount of blur in each pyramid layer relative to the input image
	protected double[] sigma;

	/**
	 * Configures the pyramid
	 *
	 * @param interpolate Interpolation function used to sub-sample.
	 * @param scales Scales of each layer in the pyramid relative to the input image
	 * @param sigmaLayers Amount of blur applied to the previous layer while constructing the pyramid.
	 * @param imageType Type of image it's processing
	 */
	public PyramidFloatGaussianScale(InterpolatePixelS<T> interpolate, double scales[], double sigmaLayers[],
									 Class<T> imageType) {
		super(imageType, scales);
		if( scales.length != sigmaLayers.length )
			throw new IllegalArgumentException("Number of scales and sigmas must be the same");

		this.interpolate = interpolate;
		this.sigmaLayers = new float[ sigmaLayers.length ];
		for( int i = 0; i < sigmaLayers.length; i++ )
			this.sigmaLayers[i] = (float) sigmaLayers[i];

		sigma = new double[ sigmaLayers.length ];
		sigma[0] = sigmaLayers[0];
		for( int i = 1; i < scales.length; i++ ) {
			// the effective blur sigma which is being applied
			double effectiveSigma = sigmaLayers[i]*scales[i-1];
			sigma[i] = Math.sqrt(sigma[i-1]*sigma[i-1] + effectiveSigma*effectiveSigma);
		}
	}


	@Override
	public void process(T input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG,
						ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN,
						ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO,
						FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG) {
		super.initialize(input.width,input.height);

		if( isSaveOriginalReference() )
			throw new IllegalArgumentException("The original reference cannot be saved");

		if( tempImage == null ) {
			tempImage = (T)input.createNew(input.width,input.height);
		}

		for( int i = 0; i < scale.length; i++ ) {
			T prev = i == 0 ? input : getLayer(i-1);
			T layer = getLayer(i);

			// Apply the requested blur to the previous layer
			BlurStorageFilter<T> blur = (BlurStorageFilter<T>) FBF.gaussian(layer.getClass(), sigmaLayers[i],-1, GIO);
			tempImage.reshape(prev.width,prev.height);
			blur.process(prev,tempImage, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG);

			// Resample the blurred image
			if( scale[i] == 1 ) {
				layer.setTo(tempImage);
			} else {
				PixelTransformAffine_F32 model = DistortSupport.transformScale(layer,tempImage, null);
				DistortImageOps.distortSingle(tempImage,layer, true, model,interpolate);
			}
		}
	}

	public InterpolatePixelS<T> getInterpolate() {
		return interpolate;
	}

	public void setInterpolate(InterpolatePixelS<T> interpolate) {
		this.interpolate = interpolate;
	}

	@Override
	public double getSampleOffset(int layer) {
		return 0;
	}

	@Override
	public double getSigma(int layer) {
		return sigma[layer];
	}

	public float[] getSigmaLayers() {
		return sigmaLayers;
	}
}
