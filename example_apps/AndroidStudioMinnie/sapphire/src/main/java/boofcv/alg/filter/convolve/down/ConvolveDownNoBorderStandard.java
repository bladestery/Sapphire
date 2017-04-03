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
package boofcv.alg.filter.convolve.down;

import boofcv.struct.convolve.Kernel1D_F32;
import boofcv.struct.convolve.Kernel1D_I32;
import boofcv.struct.convolve.Kernel2D_F32;
import boofcv.struct.convolve.Kernel2D_I32;
import boofcv.struct.image.*;


/**
 * <p>
 * Standard implementation of {@link boofcv.alg.filter.convolve.ConvolveDownNoBorder} where no special
 * optimization has been done.
 * </p>
 *
 * <p>
 * DO NOT MODIFY: This class was automatically generated by {@link GenerateConvolveDownNoBorderStandard}.
 * </p>
 * 
 * @author Peter Abeles
 */
public class ConvolveDownNoBorderStandard {

	public static void horizontal(Kernel1D_F32 kernel ,
								  GrayF32 input, GrayF32 output ,
								  int skip ) {

		if( kernel.offset != kernel.width/2 || kernel.width%2 != 1)
			throw new IllegalArgumentException("Non symmetric odd kernels not supported");

		final float[] dataSrc = input.data;
		final float[] dataDst = output.data;
		final float[] dataKer = kernel.data;

		final int offset = kernel.getOffset();
		final int kernelWidth = kernel.getWidth();

		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,kernelWidth-offset-1);
		final int height = input.height;

		final int offsetX = UtilDownConvolve.computeOffset(skip,offset); 

		for( int i = 0; i < height; i++ ) {
			int indexDst = output.startIndex + i*output.stride + offsetX/skip;
			int j = input.startIndex + i*input.stride - offset;
			final int jEnd = j+widthEnd;

			for( j += offsetX; j <= jEnd; j += skip ) {
				float total = 0;
				int indexSrc = j;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc++] ) * dataKer[k];
				}

				dataDst[indexDst++] = total;
			}
		}
	}

	public static void vertical(Kernel1D_F32 kernel,
								GrayF32 input, GrayF32 output,
								int skip ) {
		final float[] dataSrc = input.data;
		final float[] dataDst = output.data;
		final float[] dataKer = kernel.data;

		final int radius = kernel.getRadius();
		final int kernelWidth = kernel.getWidth();

		final int width = input.width;
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offsetY = UtilDownConvolve.computeOffset(skip,radius);

		for( int y = offsetY; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride;
			int i = input.startIndex + (y-radius)*input.stride;
			final int iEnd = i + width;

			for( ; i < iEnd; i++ ) {
				float total = 0;
				int indexSrc = i;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc] ) * dataKer[k];
					indexSrc += input.stride;
				}

				dataDst[indexDst++] = total;
			}
		}
	}

	public static void convolve(Kernel2D_F32 kernel ,
								GrayF32 input , GrayF32 output , int skip )
	{
		final float[] dataSrc = input.data;
		final float[] dataDst = output.data;
		final float[] dataKernel = kernel.data;

		final int radius = kernel.getRadius();
		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,radius);
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offset = UtilDownConvolve.computeOffset(skip,radius); 

		for( int y = offset; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride + offset/skip;
			for( int x = offset; x <= widthEnd; x += skip ) {
				float total = 0;
				int indexKer = 0;
				for( int ki = -radius; ki <= radius; ki++ ) {
					int indexSrc = input.startIndex+(y+ki)*input.stride+ x;
					for( int kj = -radius; kj <= radius; kj++ ) {
						total += (dataSrc[indexSrc+kj] )* dataKernel[indexKer++];
					}
				}
				dataDst[indexDst++] = total;
			}
		}
	}

	public static void horizontal(Kernel1D_I32 kernel ,
								  GrayU8 input, GrayI16 output ,
								  int skip ) {
		final byte[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int offset = kernel.getOffset();
		final int kernelWidth = kernel.getWidth();

		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,kernelWidth-offset-1);
		final int height = input.height;

		final int offsetX = UtilDownConvolve.computeOffset(skip,offset); 

		for( int i = 0; i < height; i++ ) {
			int indexDst = output.startIndex + i*output.stride + offsetX/skip;
			int j = input.startIndex + i*input.stride - offset;
			final int jEnd = j+widthEnd;

			for( j += offsetX; j <= jEnd; j += skip ) {
				int total = 0;
				int indexSrc = j;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc++] & 0xFF) * dataKer[k];
				}

				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void vertical(Kernel1D_I32 kernel,
								GrayU8 input, GrayI16 output,
								int skip ) {
		final byte[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int radius = kernel.getRadius();
		final int kernelWidth = kernel.getWidth();

		final int width = input.width;
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offsetY = UtilDownConvolve.computeOffset(skip,radius);

		for( int y = offsetY; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride;
			int i = input.startIndex + (y-radius)*input.stride;
			final int iEnd = i + width;

			for( ; i < iEnd; i++ ) {
				int total = 0;
				int indexSrc = i;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc] & 0xFF) * dataKer[k];
					indexSrc += input.stride;
				}

				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void convolve(Kernel2D_I32 kernel ,
								GrayU8 input , GrayI16 output , int skip )
	{
		final byte[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKernel = kernel.data;

		final int radius = kernel.getRadius();
		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,radius);
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offset = UtilDownConvolve.computeOffset(skip,radius); 

		for( int y = offset; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride + offset/skip;
			for( int x = offset; x <= widthEnd; x += skip ) {
				int total = 0;
				int indexKer = 0;
				for( int ki = -radius; ki <= radius; ki++ ) {
					int indexSrc = input.startIndex+(y+ki)*input.stride+ x;
					for( int kj = -radius; kj <= radius; kj++ ) {
						total += (dataSrc[indexSrc+kj] & 0xFF)* dataKernel[indexKer++];
					}
				}
				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void horizontal(Kernel1D_I32 kernel ,
								  GrayS16 input, GrayI16 output ,
								  int skip ) {
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int offset = kernel.getOffset();
		final int kernelWidth = kernel.getWidth();

		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,kernelWidth-offset-1);
		final int height = input.height;

		final int offsetX = UtilDownConvolve.computeOffset(skip,offset); 

		for( int i = 0; i < height; i++ ) {
			int indexDst = output.startIndex + i*output.stride + offsetX/skip;
			int j = input.startIndex + i*input.stride - offset;
			final int jEnd = j+widthEnd;

			for( j += offsetX; j <= jEnd; j += skip ) {
				int total = 0;
				int indexSrc = j;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc++] ) * dataKer[k];
				}

				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void vertical(Kernel1D_I32 kernel,
								GrayS16 input, GrayI16 output,
								int skip ) {
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int radius = kernel.getRadius();
		final int kernelWidth = kernel.getWidth();

		final int width = input.width;
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offsetY = UtilDownConvolve.computeOffset(skip,radius);

		for( int y = offsetY; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride;
			int i = input.startIndex + (y-radius)*input.stride;
			final int iEnd = i + width;

			for( ; i < iEnd; i++ ) {
				int total = 0;
				int indexSrc = i;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc] ) * dataKer[k];
					indexSrc += input.stride;
				}

				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void convolve(Kernel2D_I32 kernel ,
								GrayS16 input , GrayI16 output , int skip )
	{
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKernel = kernel.data;

		final int radius = kernel.getRadius();
		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,radius);
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offset = UtilDownConvolve.computeOffset(skip,radius); 

		for( int y = offset; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride + offset/skip;
			for( int x = offset; x <= widthEnd; x += skip ) {
				int total = 0;
				int indexKer = 0;
				for( int ki = -radius; ki <= radius; ki++ ) {
					int indexSrc = input.startIndex+(y+ki)*input.stride+ x;
					for( int kj = -radius; kj <= radius; kj++ ) {
						total += (dataSrc[indexSrc+kj] )* dataKernel[indexKer++];
					}
				}
				dataDst[indexDst++] = (short)total;
			}
		}
	}

	public static void horizontal(Kernel1D_I32 kernel ,
								  GrayU8 input, GrayI8 output ,
								  int skip , int divisor) {
		final byte[] dataSrc = input.data;
		final byte[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int offset = kernel.getOffset();
		final int kernelWidth = kernel.getWidth();
		int halfDivisor = divisor/2;

		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,kernelWidth-offset-1);
		final int height = input.height;

		final int offsetX = UtilDownConvolve.computeOffset(skip,offset); 

		for( int i = 0; i < height; i++ ) {
			int indexDst = output.startIndex + i*output.stride + offsetX/skip;
			int j = input.startIndex + i*input.stride - offset;
			final int jEnd = j+widthEnd;

			for( j += offsetX; j <= jEnd; j += skip ) {
				int total = 0;
				int indexSrc = j;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc++] & 0xFF) * dataKer[k];
				}

				dataDst[indexDst++] = (byte)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void vertical(Kernel1D_I32 kernel,
								GrayU8 input, GrayI8 output,
								int skip , int divisor ) {
		final byte[] dataSrc = input.data;
		final byte[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int radius = kernel.getRadius();
		final int kernelWidth = kernel.getWidth();
		int halfDivisor = divisor/2;

		final int width = input.width;
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offsetY = UtilDownConvolve.computeOffset(skip,radius);

		for( int y = offsetY; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride;
			int i = input.startIndex + (y-radius)*input.stride;
			final int iEnd = i + width;

			for( ; i < iEnd; i++ ) {
				int total = 0;
				int indexSrc = i;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc] & 0xFF) * dataKer[k];
					indexSrc += input.stride;
				}

				dataDst[indexDst++] = (byte)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void convolve(Kernel2D_I32 kernel ,
								GrayU8 input , GrayI8 output , int skip , int divisor )
	{
		final byte[] dataSrc = input.data;
		final byte[] dataDst = output.data;
		final int[] dataKernel = kernel.data;

		final int radius = kernel.getRadius();
		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,radius);
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);
		int halfDivisor = divisor/2;

		final int offset = UtilDownConvolve.computeOffset(skip,radius); 

		for( int y = offset; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride + offset/skip;
			for( int x = offset; x <= widthEnd; x += skip ) {
				int total = 0;
				int indexKer = 0;
				for( int ki = -radius; ki <= radius; ki++ ) {
					int indexSrc = input.startIndex+(y+ki)*input.stride+ x;
					for( int kj = -radius; kj <= radius; kj++ ) {
						total += (dataSrc[indexSrc+kj] & 0xFF)* dataKernel[indexKer++];
					}
				}
				dataDst[indexDst++] = (byte)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void horizontal(Kernel1D_I32 kernel ,
								  GrayS16 input, GrayI16 output ,
								  int skip , int divisor) {
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int offset = kernel.getOffset();
		final int kernelWidth = kernel.getWidth();
		int halfDivisor = divisor/2;

		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,kernelWidth-offset-1);
		final int height = input.height;

		final int offsetX = UtilDownConvolve.computeOffset(skip,offset); 

		for( int i = 0; i < height; i++ ) {
			int indexDst = output.startIndex + i*output.stride + offsetX/skip;
			int j = input.startIndex + i*input.stride - offset;
			final int jEnd = j+widthEnd;

			for( j += offsetX; j <= jEnd; j += skip ) {
				int total = 0;
				int indexSrc = j;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc++] ) * dataKer[k];
				}

				dataDst[indexDst++] = (short)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void vertical(Kernel1D_I32 kernel,
								GrayS16 input, GrayI16 output,
								int skip , int divisor ) {
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKer = kernel.data;

		final int radius = kernel.getRadius();
		final int kernelWidth = kernel.getWidth();
		int halfDivisor = divisor/2;

		final int width = input.width;
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);

		final int offsetY = UtilDownConvolve.computeOffset(skip,radius);

		for( int y = offsetY; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride;
			int i = input.startIndex + (y-radius)*input.stride;
			final int iEnd = i + width;

			for( ; i < iEnd; i++ ) {
				int total = 0;
				int indexSrc = i;
				for( int k = 0; k < kernelWidth; k++ ) {
					total += (dataSrc[indexSrc] ) * dataKer[k];
					indexSrc += input.stride;
				}

				dataDst[indexDst++] = (short)((total+halfDivisor)/divisor);
			}
		}
	}

	public static void convolve(Kernel2D_I32 kernel ,
								GrayS16 input , GrayI16 output , int skip , int divisor )
	{
		final short[] dataSrc = input.data;
		final short[] dataDst = output.data;
		final int[] dataKernel = kernel.data;

		final int radius = kernel.getRadius();
		final int widthEnd = UtilDownConvolve.computeMaxSide(input.width,skip,radius);
		final int heightEnd = UtilDownConvolve.computeMaxSide(input.height,skip,radius);
		int halfDivisor = divisor/2;

		final int offset = UtilDownConvolve.computeOffset(skip,radius); 

		for( int y = offset; y <= heightEnd; y += skip ) {
			int indexDst = output.startIndex + (y/skip)*output.stride + offset/skip;
			for( int x = offset; x <= widthEnd; x += skip ) {
				int total = 0;
				int indexKer = 0;
				for( int ki = -radius; ki <= radius; ki++ ) {
					int indexSrc = input.startIndex+(y+ki)*input.stride+ x;
					for( int kj = -radius; kj <= radius; kj++ ) {
						total += (dataSrc[indexSrc+kj] )* dataKernel[indexKer++];
					}
				}
				dataDst[indexDst++] = (short)((total+halfDivisor)/divisor);
			}
		}
	}

}