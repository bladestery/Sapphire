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

package boofcv.factory.feature.detect.intensity;

import org.hamcrest.Factory;

import boofcv.alg.feature.detect.intensity.FastCornerIntensity;
import boofcv.alg.feature.detect.intensity.HarrisCornerIntensity;
import boofcv.alg.feature.detect.intensity.ShiTomasiCornerIntensity;
import boofcv.alg.feature.detect.intensity.impl.*;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernel;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageGray;
import sapphire.app.SapphireObject;

/**
 * Factory for creating various types of interest point intensity algorithms.
 *
 * @see FactoryIntensityPoint
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class FactoryIntensityPointAlg implements SapphireObject {
	public FactoryIntensityPointAlg() {}

	/**
	 * Common interface for creating a {@link boofcv.alg.feature.detect.intensity.FastCornerIntensity} from different image types.
	 *
	 * @param pixelTol How different pixels need to be to be considered part of a corner. Image dependent.  Try 20 to start.
	 * @param minCont Minimum number of continue pixels in a circle for it ot be a corner.  Can be 9,10,11 or 12.
	 * @param imageType Type of input image it is computed form.
	 * @return Fast corner
	 */
	 public <T extends ImageGray>
	FastCornerIntensity<T> fast(int pixelTol, int minCont, Class<T> imageType)
	{
		FastHelper<T> helper;

		if( imageType == GrayF32.class )
			helper = (FastHelper)new ImplFastHelper_F32(pixelTol);
		else if( imageType == GrayU8.class )
			helper = (FastHelper)new ImplFastHelper_U8(pixelTol);
		else
			throw new IllegalArgumentException("Unsupported image type "+imageType);

		if( minCont == 9 ) {
			return new ImplFastIntensity9<>(helper);
		} else if( minCont == 10 ) {
			return new ImplFastIntensity10<>(helper);
		} else if( minCont == 11 ) {
			return new ImplFastIntensity11<>(helper);
		} else if( minCont == 12 ) {
			return new ImplFastIntensity12<>(helper);
		} else {
			throw new IllegalArgumentException("Specified minCont is not supported");
		}
	}

	/**
	 * Common interface for creating a {@link boofcv.alg.feature.detect.intensity.HarrisCornerIntensity} from different
	 * image types.
	 *
	 * @param windowRadius Size of the feature it is detects,Try 2.
	 * @param kappa Tuning parameter, typically a small number around 0.04
	 * @param weighted Is the gradient weighted using a Gaussian distribution?  Weighted is much slower than unweighted.
	 * @param derivType Image derivative type it is computed from.  @return Harris corner
	 */
	 public <D extends ImageGray>
	HarrisCornerIntensity<D> harris(int windowRadius, float kappa, boolean weighted, Class<D> derivType, GeneralizedImageOps GIO, FactoryKernelGaussian FKG)
	{
		if( derivType == GrayF32.class ) {
			if( weighted )
				return (HarrisCornerIntensity<D>)new ImplHarrisCornerWeighted_F32(windowRadius,kappa, FKG);
			else
				return (HarrisCornerIntensity<D>)new ImplHarrisCorner_F32(windowRadius,kappa, GIO);

		} else if( derivType == GrayS16.class ) {
			if( weighted )
				return (HarrisCornerIntensity<D>)new ImplHarrisCornerWeighted_S16(windowRadius,kappa, FKG);
			else
				return (HarrisCornerIntensity<D>)new ImplHarrisCorner_S16(windowRadius,kappa, GIO);

		}else
			throw new IllegalArgumentException("Unknown image type "+derivType);
	}

	/**
	 * Common interface for creating a {@link boofcv.alg.feature.detect.intensity.ShiTomasiCornerIntensity} from
	 * different image types.
	 *
	 * @param windowRadius Size of the feature it detects, Try 2.
	 * @param weighted Should the it be weighted by a Gaussian kernel?  Unweighted is much faster.
	 * @param derivType Image derivative type it is computed from.
	 * @return KLT corner
	 */
	 public <D extends ImageGray>
	ShiTomasiCornerIntensity<D> shiTomasi(int windowRadius, boolean weighted, Class<D> derivType, GeneralizedImageOps GIO, FactoryKernelGaussian FKG)
	{
		if( derivType == GrayF32.class ) {
			if( weighted )
				return (ShiTomasiCornerIntensity<D>)new ImplShiTomasiCornerWeighted_F32(windowRadius, FKG);
			else
				return (ShiTomasiCornerIntensity<D>)new ImplShiTomasiCorner_F32(windowRadius, GIO);
		} else if( derivType == GrayS16.class ) {
			if( weighted )
				return (ShiTomasiCornerIntensity<D>)new ImplShiTomasiCornerWeighted_S16(windowRadius, FKG);
			else
				return (ShiTomasiCornerIntensity<D>)new ImplShiTomasiCorner_S16(windowRadius, GIO);
		} else
			throw new IllegalArgumentException("Unknown image type "+derivType);
	}
}
