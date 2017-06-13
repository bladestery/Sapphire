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

package boofcv.abst.flow;

import boofcv.alg.InputSanityCheck;
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
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.flow.BroxWarpingSpacial;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.flow.ImageFlow;
import boofcv.struct.image.FactoryImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * Implementation of {@link BroxWarpingSpacial} for {@link boofcv.alg.flow.HornSchunck}.
 *
 * @author Peter Abeles
 */
public class BroxWarpingSpacial_to_DenseOpticalFlow<T extends ImageGray>
	implements DenseOpticalFlow<T>
{
	private ImageType IT;
	BroxWarpingSpacial<T> brox;
	Class<T> imageType;

	public BroxWarpingSpacial_to_DenseOpticalFlow(BroxWarpingSpacial<T> brox,
												  Class<T> imageType ) {
		this.brox = brox;
		this.imageType = imageType;
	}

	@Override
	public void process(T source, T destination, ImageFlow flow, InputSanityCheck ISC, ConvolveImageNoBorder CINB, ConvolveJustBorder_General CJBG,
						GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveNormalizedNaive CNN,
						ConvolveNormalized_JustBorder CNJB, ConvolveNormalized CN, GBlurImageOps GBIO, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						FactoryKernelGaussian FKG, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM,
						GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, FactoryBlurFilter FBF, ConvertImage CI, UtilWavelet UW,
						DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, ImageType IT, FactoryImageBorder FIB, FactoryInterpolation FI,
						FactoryDistort FDs) {

		brox.process(source, destination);

		GrayF32 flowX = brox.getFlowX();
		GrayF32 flowY = brox.getFlowY();

		int index = 0;
		for( int y = 0; y < flow.height; y++){
			for( int x = 0; x < flow.width; x++, index++ ){
				ImageFlow.D d = flow.unsafe_get(x,y);
				d.x = flowX.data[index];
				d.y = flowY.data[index];
			}
		}
	}

	@Override
	public ImageType<T> getInputType() {
		return IT.single(imageType);
	}
}
