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

package boofcv.alg.feature.orientation;

import boofcv.abst.feature.orientation.OrientationImage;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.GradientSobel;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.misc.BoofMiscOps;
import boofcv.struct.ImageRectangle;
import boofcv.struct.convolve.Kernel2D_F32;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;


/**
 * Computes the orientation of a region by computing a weighted sum of each pixel's intensity
 * using their respective sine and cosine values.
 *
 * @author Peter Abeles
 */
public abstract class OrientationImageAverage<T extends ImageGray> implements OrientationImage<T> {

	// input image
	protected T image;

	// local variable used to define the region being examined.
	// this makes it easy to avoid going outside the image
	protected ImageRectangle rect = new ImageRectangle();

	// converts from object radius to sample region scale
	protected double objectToSample;

	// Radius of the region it will sample
	protected int sampleRadius;

	// cosine values for each pixel
	protected Kernel2D_F32 kerCosine;
	// sine values for each pixel
	protected Kernel2D_F32 kerSine;

	public OrientationImageAverage(double objectToSample,int defaultRadius, FactoryKernelGaussian FKG) {
		this.objectToSample = objectToSample;
		setObjectRadius(defaultRadius, FKG);
	}

	@Override
	public void setImage(T image, InputSanityCheck ISC, GeneralizedImageOps GIO, DerivativeHelperFunctions DHF, ConvolveImageNoBorder CINB,
						 ConvolveJustBorder_General CJBG, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryKernelGaussian FKG, GImageMiscOps GIMO, ImageMiscOps IMO, ConvertImage CI,
						 FactoryImageBorder FIB, ConvolveNormalizedNaive CNN, ConvolveNormalized_JustBorder CNJB,
						 ConvolveNormalized CN) {
		this.image = image;
	}

	@Override
	public void setObjectRadius(double objectRadius, FactoryKernelGaussian FKG) {
		sampleRadius = (int)Math.ceil(objectRadius* objectToSample);

		int w = sampleRadius*2+1;
		kerCosine = new Kernel2D_F32(w);
		kerSine = new Kernel2D_F32(w);

		for(int y = -sampleRadius; y <= sampleRadius; y++ ) {
			int pixelY = y+ sampleRadius;
			for(int x = -sampleRadius; x <= sampleRadius; x++ ) {
				int pixelX = x+ sampleRadius;
				float r = (float)Math.sqrt(x*x+y*y);
				kerCosine.set(pixelX,pixelY,(float)x/r);
				kerSine.set(pixelX,pixelY,(float)y/r);
			}
		}
		kerCosine.set(sampleRadius, sampleRadius,0);
		kerSine.set(sampleRadius, sampleRadius,0);
	}

	@Override
	public double compute(double X, double Y, FastHessianFeatureDetector FHFD) {

		int c_x = (int)(X+0.5);
		int c_y = (int)(Y+0.5);

		// compute the visible region while taking in account
		// the image borders
		rect.x0 = c_x- sampleRadius;
		rect.y0 = c_y- sampleRadius;
		rect.x1 = c_x+ sampleRadius +1;
		rect.y1 = c_y+ sampleRadius +1;

		BoofMiscOps.boundRectangleInside(image,rect);

		return computeAngle(c_x,c_y);
	}

	protected abstract double computeAngle( int c_x , int c_y );
}
