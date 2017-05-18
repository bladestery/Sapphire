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

package boofcv.alg.filter.convolve;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.kernel.KernelMath;
import boofcv.struct.convolve.*;
import boofcv.struct.image.*;
import sapphire.app.SapphireObject;

/**
 * Convolves a kernel across an image and scales the kernel such that the sum of the portion inside
 * the image sums up to one.
 *
 * @author Peter Abeles
 */
public class ConvolveNormalized implements SapphireObject {
	public ConvolveNormalized() {}
	/**
	 * Performs a horizontal 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void horizontal(Kernel1D_F32 kernel, GrayF32 image, GrayF32 dest , InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width ) {
			CNN.horizontal(kernel,image,dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-4f ) {
				Kernel1D_F32 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.horizontal(kernel,image,dest, ISC);
			CNJB.horizontal(kernel,image,dest);
		}
	}

	/**, ConvolveImageNoBorder.class, ConvolveJustBorder_General.class
	 * Performs a horizontal 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void horizontal(Kernel1D_F64 kernel, GrayF64 image, GrayF64 dest, InputSanityCheck ISC , ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width ) {
			CNN.horizontal(kernel,image,dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-8f ) {
				Kernel1D_F64 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.horizontal(kernel,image,dest, ISC);
			CNJB.horizontal(kernel,image,dest);
		}
	}

	/**
	 * Performs a vertical 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void vertical(Kernel1D_F32 kernel, GrayF32 image, GrayF32 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.height ) {
			CNN.vertical(kernel,image,dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-4f ) {
				Kernel1D_F32 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.vertical(kernel,image,dest, ISC);
			CNJB.vertical(kernel,image,dest);
		}
	}

	/**
	 * Performs a vertical 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void vertical(Kernel1D_F64 kernel, GrayF64 image, GrayF64 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.height ) {
			CNN.vertical(kernel,image,dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-8f ) {
				Kernel1D_F64 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.vertical(kernel,image,dest, ISC);
			CNJB.vertical(kernel,image,dest);
		}
	}

	/**
	 * Performs a 2D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void convolve(Kernel2D_F32 kernel, GrayF32 image, GrayF32 dest , InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width || kernel.width >= image.height ) {
			CNN.convolve(kernel, image, dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-4f ) {
				Kernel2D_F32 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.convolve(kernel, image, dest, ISC);
			CNJB.convolve(kernel, image, dest);
		}
	}

	/**
	 * Performs a 2D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void convolve(Kernel2D_F64 kernel, GrayF64 image, GrayF64 dest , InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width || kernel.width >= image.height ) {
			CNN.convolve(kernel, image, dest);
		} else {
			if( Math.abs(kernel.computeSum() - 1.0f) > 1e-8f ) {
				Kernel2D_F64 k = kernel.copy();
				KernelMath.normalizeSumToOne(k);
				kernel = k;
			}
			CINB.convolve(kernel, image, dest, ISC);
			CNJB.convolve(kernel, image, dest);
		}
	}

	/**
	 * Performs a horizontal 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void horizontal(Kernel1D_I32 kernel, GrayU8 image, GrayI8 dest, InputSanityCheck ISC , ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width ) {
			CNN.horizontal(kernel, image, dest);
		} else {
			CINB.horizontal(kernel, image, dest, kernel.computeSum(), ISC);
			CNJB.horizontal(kernel, image, dest);
		}
	}

	/**
	 * Performs a vertical 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void vertical(Kernel1D_I32 kernel, GrayU8 image, GrayI8 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.height ) {
			CNN.vertical(kernel, image, dest);
		} else {
			CINB.vertical(kernel, image, dest, kernel.computeSum(), ISC);
			CNJB.vertical(kernel, image, dest);
		}
	}

	/**
	 * Performs a 2D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void convolve(Kernel2D_I32 kernel, GrayU8 image, GrayI8 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width || kernel.width >= image.height ) {
			CNN.convolve(kernel, image, dest);
		} else {
			CINB.convolve(kernel, image, dest, kernel.computeSum(), ISC);
			CNJB.convolve(kernel, image, dest);
		}
	}

	/**
	 * Performs a horizontal 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void horizontal(Kernel1D_I32 kernel, GrayS16 image, GrayI16 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width ) {
			CNN.horizontal(kernel, image, dest);
		} else {
			CINB.horizontal(kernel, image, dest, kernel.computeSum(), ISC);
			CNJB.horizontal(kernel, image, dest);
		}
	}

	/**
	 * Performs a horizontal 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void horizontal(Kernel1D_I32 kernel, GrayS32 image, GrayS32 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width ) {
			CNN.horizontal(kernel, image, dest);
		} else {
			CINB.horizontal(kernel, image, dest, kernel.computeSum(), ISC);
			CNJB.horizontal(kernel, image, dest);
		}
	}

	/**
	 * Performs a vertical 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void vertical(Kernel1D_I32 kernel, GrayS16 image, GrayI16 dest , InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.height ) {
			CNN.vertical(kernel,image,dest);
		} else {
			CINB.vertical(kernel,image,dest,kernel.computeSum(), ISC);
			CNJB.vertical(kernel,image,dest);
		}
	}

	/**
	 * Performs a vertical 1D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void vertical(Kernel1D_I32 kernel, GrayS32 image, GrayS32 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.height ) {
			CNN.vertical(kernel,image,dest);
		} else {
			CINB.vertical(kernel,image,dest,kernel.computeSum(), ISC);
			CNJB.vertical(kernel,image,dest);
		}
	}

	/**
	 * Performs a 2D convolution across the image while re-normalizing the kernel depending on its
	 * overlap with the image.
	 *
	 * @param image	 The original image. Not modified.
	 * @param dest	 Where the resulting image is written to. Modified.
	 * @param kernel The kernel that is being convolved. Not modified.
	 */
	public void convolve(Kernel2D_I32 kernel, GrayS16 image, GrayI16 dest, InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB ) {
		ISC.checkSameShape(image, dest);

		if( kernel.width >= image.width || kernel.width >= image.height ) {
			CNN.convolve(kernel,image,dest);
		} else {
			CINB.convolve(kernel,image,dest,kernel.computeSum(), ISC);
			CNJB.convolve(kernel,image,dest);
		}
	}
}
