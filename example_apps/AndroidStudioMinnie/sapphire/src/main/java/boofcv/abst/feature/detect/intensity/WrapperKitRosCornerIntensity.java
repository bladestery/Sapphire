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

package boofcv.abst.feature.detect.intensity;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.intensity.KitRosCornerIntensity;
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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wrapper around children of {@link boofcv.alg.feature.detect.intensity.GradientCornerIntensity}.
 * 
 * @author Peter Abeles
 */
public class WrapperKitRosCornerIntensity<I extends ImageGray,D extends ImageGray>
		extends BaseGeneralFeatureIntensity<I,D>
{
	Method m;

	public WrapperKitRosCornerIntensity(Class<D> derivType ) {
		try {
			m = KitRosCornerIntensity.class.getMethod("process",GrayF32.class,derivType,derivType,derivType,derivType,derivType);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void process(I image , D derivX, D derivY, D derivXX, D derivYY, D derivXY, GImageMiscOps GIMO, ImageMiscOps IMO,
						InputSanityCheck ISC, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN,
						GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI,
						ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS,
						ThresholdImageOps TIO, ConvolveJustBorder_General CJBG) {
		init(image.width,image.height, GIMO, IMO);

		try {
			m.invoke(null,intensity,derivX,derivY,derivXX,derivYY,derivXY,ISC);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public QueueCorner getCandidatesMin() {
		return null;
	}

	@Override
	public QueueCorner getCandidatesMax() {
		return null;
	}

	@Override
	public boolean getRequiresGradient() {
		return true;
	}

	@Override
	public boolean getRequiresHessian() {
		return true;
	}

	@Override
	public boolean hasCandidates() {
		return false;
	}

	/**
	 * There is no ignore border, unless the derivative that it is computed from has an ignore border.
	 */
	@Override
	public int getIgnoreBorder() {
		return 0;
	}

	@Override
	public boolean localMinimums() {
		return false;
	}

	@Override
	public boolean localMaximums() {
		return true;
	}
}
