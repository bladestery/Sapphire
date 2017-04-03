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

package boofcv.alg.filter.basic.impl;

import boofcv.struct.image.*;

/**
 * <p>
 * Contains implementations of algorithms in {@link boofcv.alg.filter.basic.GrayImageOps}.
 * </p>
 * 
 * <p>
 * WARNING: Do not modify.  Automatically generated by {@link GenerateImplGrayImageOps}.
 * </p>
 */
public class ImplGrayImageOps {

	public static void invert(GrayF32 input, float max , GrayF32 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(GrayF32 input, float beta, float max , GrayF32 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				float val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void stretch(GrayF32 input, double gamma, float beta, float max , GrayF32 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				float val = (float)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void invert(GrayU8 input, int max , GrayU8 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (byte)(max - (input.data[indexSrc++]& 0xFF));
			}
		}
	}

	public static void brighten(GrayU8 input, int beta, int max , GrayU8 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]& 0xFF) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (byte)val;
			}
		}
	}

	public static void stretch(GrayU8 input, double gamma, int beta, int max , GrayU8 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++]& 0xFF)* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (byte)val;
			}
		}
	}

	public static void invert(GrayS16 input, int max , GrayS16 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (short)(max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(GrayS16 input, int beta, int max , GrayS16 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void stretch(GrayS16 input, double gamma, int beta, int max , GrayS16 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void invert(GrayU16 input, int max , GrayU16 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (short)(max - (input.data[indexSrc++]& 0xFFFF));
			}
		}
	}

	public static void brighten(GrayU16 input, int beta, int max , GrayU16 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]& 0xFFFF) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void stretch(GrayU16 input, double gamma, int beta, int max , GrayU16 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++]& 0xFFFF)* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = (short)val;
			}
		}
	}

	public static void invert(GrayS32 input, int max , GrayS32 output) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				output.data[indexDst++] = (max - (input.data[indexSrc++]));
			}
		}
	}

	public static void brighten(GrayS32 input, int beta, int max , GrayS32 output ) {

		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (input.data[indexSrc++]) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}

	public static void stretch(GrayS32 input, double gamma, int beta, int max , GrayS32 output ) {
		for (int y = 0; y < input.height; y++) {
			int indexSrc = input.startIndex + input.stride*y;
			int indexDst = output.startIndex + output.stride*y;

			for (int x = 0; x < input.width; x++) {
				int val = (int)((input.data[indexSrc++])* gamma) + beta;
				if (val > max) val = max;
				if (val < 0) val = 0;
				output.data[indexDst++] = val;
			}
		}
	}


}