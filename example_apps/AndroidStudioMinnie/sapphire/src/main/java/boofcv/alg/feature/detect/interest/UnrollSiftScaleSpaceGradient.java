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
import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.struct.image.GrayF32;

import java.util.ArrayList;
import java.util.List;

/**
 * Precomputes the gradient for all scales in the scale-space and saves them in a list.  Since it saves the entire
 * scale space it can take up a bit of memory, but allows quick random look up of images.
 *
 * @author Peter Abeles
 */
public class UnrollSiftScaleSpaceGradient {
	private static FactoryDerivative FD;
	private static GeneralizedImageOps GIO;
	private static FactoryImageBorder FIB;
	private static InputSanityCheck ISC;
	private static DerivativeHelperFunctions DHF;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveJustBorder_General CJBG;
	private static GradientSobel_Outer GSO;
	private static GradientSobel_UnrolledOuter GSUO;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ConvolveNormalized CN;
	// input scale space
	SiftScaleSpace scaleSpace;

	// scale images that are using
	List<ImageScale> usedScales = new ArrayList<>();
	// storage for all possible scales
	List<ImageScale> allScales = new ArrayList<>();

	// used to compute the image gradient
	ImageGradient<GrayF32,GrayF32> gradient = FD.three(GrayF32.class,null, GIO, FIB);

	public UnrollSiftScaleSpaceGradient(SiftScaleSpace scaleSpace) {
		this.scaleSpace = scaleSpace;

		// create one image for each scale to minimize memory being created/destroyed
		int numScales = scaleSpace.getNumScales()*scaleSpace.getTotalOctaves();
		for (int i = 0; i < numScales; i++) {
			allScales.add( new ImageScale());
		}
	}

	/**
	 * Sets the input image.  Scale-space is computed and unrolled from this image
	 * @param image
	 */
	public void setImage(GrayF32 image) {

		scaleSpace.initialize(image, FIB, ISC, CNN, CINB, CNJB, CN);

		usedScales.clear();
		do {
			for (int i = 0; i < scaleSpace.getNumScales(); i++) {
				GrayF32 scaleImage = scaleSpace.getImageScale(i);
				double sigma = scaleSpace.computeSigmaScale(i);
				double pixelCurrentToInput = scaleSpace.pixelScaleCurrentToInput();

				ImageScale scale = allScales.get(usedScales.size());
				scale.derivX.reshape(scaleImage.width,scaleImage.height);
				scale.derivY.reshape(scaleImage.width,scaleImage.height);

				gradient.process(scaleImage,scale.derivX,scale.derivY, ISC,DHF, CINB, CJBG,GSO,GSUO);
				scale.imageToInput = pixelCurrentToInput;
				scale.sigma = sigma;

				usedScales.add(scale);
			}
		} while( scaleSpace.computeNextOctave(ISC, CNN, CINB, CNJB, CN) );
	}


	/**
	 * Looks up the image which is closest specified sigma
	 */
	public ImageScale lookup( double sigma ) {
		ImageScale best = null;
		double bestValue = Double.MAX_VALUE;

		for (int i = 0; i < usedScales.size(); i++) {
			ImageScale image = usedScales.get(i);
			double difference = Math.abs(sigma-image.sigma);
			if( difference < bestValue ) {
				bestValue = difference;
				best = image;
			}
		}
		return best;
	}

	public static class ImageScale {
		public GrayF32 derivX = new GrayF32(1,1);
		public GrayF32 derivY = new GrayF32(1,1);
		public double imageToInput;
		public double sigma;
	}
}
