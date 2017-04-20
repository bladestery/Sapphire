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

package boofcv.factory.filter.blur;

import boofcv.abst.filter.blur.BlurStorageFilter;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.struct.image.ImageGray;
import sapphire.app.SapphireObject;

/**
 * Factory for creating different blur image filters.
 *
 * @author Peter Abeles
 */
public class FactoryBlurFilter implements SapphireObject {
	public FactoryBlurFilter() {}

	/**
	 * Creates a median filter for the specified image type.
	 *
	 * @param type Image type.
	 * @param radius Size of the filter.
	 * @return Median image filter.
	 */
	public <T extends ImageGray> BlurStorageFilter<T> median(Class<T> type , int radius, GeneralizedImageOps GIO) {
		return new BlurStorageFilter<>("median", type, radius, GIO);
	}

	/**
	 * Creates a mean filter for the specified image type.
	 *
	 * @param type Image type.
	 * @param radius Size of the filter.
	 * @return mean image filter.
	 */
	public <T extends ImageGray> BlurStorageFilter<T> mean(Class<T> type , int radius, GeneralizedImageOps GIO ) {
		return new BlurStorageFilter<>("mean", type, radius, GIO);
	}

	/**
	 * Creates a Gaussian filter for the specified image type.
	 *
	 * @param type Image type.
	 * @param radius Size of the filter.
	 * @return mean image filter.
	 */
	public <T extends ImageGray> BlurStorageFilter<T> gaussian(Class<T> type , double sigma , int radius, GeneralizedImageOps GIO) {
		return new BlurStorageFilter<>("gaussian", type, sigma, radius, GIO);
	}
}
