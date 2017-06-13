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

package boofcv.factory.flow;

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.abst.flow.*;
import boofcv.alg.filter.derivative.GImageDerivativeOps;
import boofcv.alg.flow.*;
import boofcv.alg.interpolate.InterpolatePixelS;
import boofcv.alg.tracker.klt.PkltConfig;
import boofcv.alg.tracker.klt.PyramidKltTracker;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.BorderType;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.derivative.FactoryDerivative;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.factory.tracker.FactoryTrackerAlg;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.pyramid.PyramidDiscrete;
import sapphire.compiler.GIOGenerator;

/**
 * Creates implementations of {@link DenseOpticalFlow}.
 *
 * @author Peter Abeles
 */
public class FactoryDenseOpticalFlow {
	/**
	 * Compute optical flow using {@link PyramidKltTracker}.
	 *
	 * @see DenseOpticalFlowKlt
	 *
	 * @param configKlt Configuration for KLT.  If null then default values are used.
	 * @param radius Radius of square region.
	 * @param inputType Type of input image.
	 * @param derivType Type of derivative image.  If null then default is used.
	 * @param <I> Input image type.
	 * @param <D> Derivative image type.
	 * @return DenseOpticalFlow
	 */
	public static <I extends ImageGray, D extends ImageGray>
	DenseOpticalFlow<I> flowKlt(PkltConfig configKlt, int radius , Class<I> inputType , Class<D> derivType, FactoryPyramid FP, FactoryKernelGaussian FKG,
								ImageType IT, GeneralizedImageOps GIO, FactoryImageBorder FIB, FactoryDerivative FD, FactoryInterpolation FI) {

		if( configKlt == null )
			configKlt = new PkltConfig();

		if( derivType == null ) {
			derivType = GImageDerivativeOps.getDerivativeType(inputType);
		}

		int numLayers = configKlt.pyramidScaling.length;

		PyramidDiscrete<I> pyramidA = FP.discreteGaussian(configKlt.pyramidScaling, -1, 2, true, inputType, FKG);
		PyramidDiscrete<I> pyramidB = FP.discreteGaussian(configKlt.pyramidScaling, -1, 2, true, inputType, FKG);

		PyramidKltTracker<I, D> tracker = FactoryTrackerAlg.kltPyramid(configKlt.config, inputType, derivType, FI);
		DenseOpticalFlowKlt<I, D> flowKlt = new DenseOpticalFlowKlt<>(tracker, numLayers, radius);
		ImageGradient<I, D> gradient = FD.sobel(inputType,derivType, GIO, FIB);

		return new FlowKlt_to_DenseOpticalFlow<>(flowKlt, gradient, pyramidA, pyramidB, inputType, derivType, IT, GIO);
	}

	/**
	 * Creates a pyramidal block
	 *
	 * @see boofcv.alg.flow.DenseOpticalFlowBlockPyramid
	 * @see boofcv.alg.flow.UtilDenseOpticalFlow#standardPyramid(int, int, double, double, int, int, Class)
	 *
	 * @param config Configuration for block pyramid
	 * @param <T>
	 * @return
	 */
	public static <T extends ImageGray>
	DenseOpticalFlow<T> region( ConfigOpticalFlowBlockPyramid config , Class<T> imageType, ImageType IT)
	{
		if( config == null )
			config = new ConfigOpticalFlowBlockPyramid();

		DenseOpticalFlowBlockPyramid<T> alg;
		if( imageType == GrayU8.class )
			alg = (DenseOpticalFlowBlockPyramid)new DenseOpticalFlowBlockPyramid.U8(
					config.searchRadius,config.regionRadius,config.maxPerPixelError);
		else if( imageType == GrayF32.class )
			alg = (DenseOpticalFlowBlockPyramid)new DenseOpticalFlowBlockPyramid.F32(
					config.searchRadius,config.regionRadius,config.maxPerPixelError);
		else
			throw new IllegalArgumentException("Unsupported image type "+imageType);

		return new FlowBlock_to_DenseOpticalFlow<>(alg, config.pyramidScale, config.maxPyramidLayers, imageType, IT);
	}

	/**
	 * The original Horn-Schunck algorithm.  Only good for very small motions.
	 *
	 * @see HornSchunck
	 *
	 * @param config Configuration parameters.  If null then default is used.
	 * @param imageType Type of input gray scale image
	 * @return dense optical flow
	 */
	public static <T extends ImageGray,D extends ImageGray>
	DenseOpticalFlow<T> hornSchunck( ConfigHornSchunck config , Class<T> imageType, ImageType IT)
	{
		if( config == null )
			config = new ConfigHornSchunck();

		HornSchunck<T,D> alg;
		if( imageType == GrayU8.class )
			alg = (HornSchunck)new HornSchunck_U8(config.alpha,config.numIterations);
		else
		if( imageType == GrayF32.class )
			alg = (HornSchunck)new HornSchunck_F32(config.alpha,config.numIterations);
		else
			throw new IllegalArgumentException("Unsupported image type "+imageType);

		return new HornSchunck_to_DenseOpticalFlow<>(alg, IT.single(imageType));
	}

	/**
	 * Creates an instance of {@link HornSchunckPyramid}
	 *
	 * @see HornSchunckPyramid
	 *
	 * @param config Configuration parameters.  If null defaults will be used.
	 * @return Dense optical flow implementation of HornSchunckPyramid
	 */
	public static <T extends ImageGray>
	DenseOpticalFlow<T> hornSchunckPyramid( ConfigHornSchunckPyramid config , Class<T> imageType, FactoryImageBorder FIB, FactoryInterpolation FI)
	{
		if( config == null )
			config = new ConfigHornSchunckPyramid();

		InterpolatePixelS<GrayF32> interpolate =
				FI.createPixelS(0,255,config.interpolation, BorderType.EXTENDED, GrayF32.class, FIB);

		HornSchunckPyramid<T> alg = new HornSchunckPyramid<>(config, interpolate);

		return new HornSchunckPyramid_to_DenseOpticalFlow<>(alg, imageType);
	}

	public static <T extends ImageGray>
	DenseOpticalFlow<T> broxWarping( ConfigBroxWarping config , Class<T> imageType, FactoryImageBorder FIB, FactoryInterpolation FI)
	{
		if( config == null )
			config = new ConfigBroxWarping();

		InterpolatePixelS<GrayF32> interpolate =
				FI.createPixelS(0,255,config.interpolation, BorderType.EXTENDED, GrayF32.class, FIB);

		BroxWarpingSpacial<T> alg = new BroxWarpingSpacial<>(config, interpolate);

		return new BroxWarpingSpacial_to_DenseOpticalFlow<>(alg, imageType);
	}
}
