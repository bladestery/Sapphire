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

import boofcv.abst.filter.derivative.ImageGradient;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.detect.interest.FastHessianFeatureDetector;
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
import boofcv.alg.flow.DenseOpticalFlowKlt;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.pyramid.PyramidOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.core.image.border.FactoryImageBorderAlgs;
import boofcv.core.image.border.ImageBorderValue;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.flow.ImageFlow;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import boofcv.struct.pyramid.ImagePyramid;
import sapphire.compiler.ITGenerator;

import java.lang.reflect.Array;

/**
 * Wrapper around {@link DenseOpticalFlowKlt} for {@link DenseOpticalFlow}.
 *
 * @author Peter Abeles
 */
public class FlowKlt_to_DenseOpticalFlow<I extends ImageGray, D extends ImageGray>
	implements DenseOpticalFlow<I>
{
	private static InputSanityCheck ISC;
	private static DerivativeHelperFunctions DHF;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveJustBorder_General CJBG;
	private static GradientSobel_Outer GSO;
	private static GradientSobel_UnrolledOuter GSUO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ConvolveNormalized CN;
	private static GBlurImageOps GBIO;
	private static GeneralizedImageOps GIO;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static FactoryImageBorderAlgs FIBA;
	private static ImageBorderValue IBV;
	private static FastHessianFeatureDetector FHFD;
	private static FactoryImageBorder FIB;
	private static FactoryBlurFilter FBF;
	private static ConvertImage CI;
	private static UtilWavelet UW;
	private static ImageType IT;
	DenseOpticalFlowKlt<I,D> flowKlt;
	ImageGradient<I,D> gradient;

	ImagePyramid<I> pyramidSrc;
	ImagePyramid<I> pyramidDst;

	D[] srcDerivX;
	D[] srcDerivY;

	ImageType<I> imageType;

	public FlowKlt_to_DenseOpticalFlow(DenseOpticalFlowKlt<I, D> flowKlt,
									   ImageGradient<I, D> gradient,
									   ImagePyramid<I> pyramidSrc,
									   ImagePyramid<I> pyramidDst,
									   Class<I> inputType , Class<D> derivType ) {
		if( pyramidSrc.getNumLayers() != pyramidDst.getNumLayers() )
			throw new IllegalArgumentException("Pyramids do not have the same number of layers!");

		this.flowKlt = flowKlt;
		this.gradient = gradient;
		this.pyramidSrc = pyramidSrc;
		this.pyramidDst = pyramidDst;

		srcDerivX = (D[])Array.newInstance(derivType,pyramidSrc.getNumLayers());
		srcDerivY = (D[])Array.newInstance(derivType,pyramidSrc.getNumLayers());

		for( int i = 0; i < srcDerivX.length; i++ ) {
			srcDerivX[i] = GIO.createSingleBand(derivType,1,1);
			srcDerivY[i] = GIO.createSingleBand(derivType,1,1);
		}

		imageType = IT.single(inputType);
	}

	@Override
	public void process(I source, I destination, ImageFlow flow) {
		pyramidSrc.process(source, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW);
		pyramidDst.process(destination, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW);

		PyramidOps.reshapeOutput(pyramidSrc,srcDerivX);
		PyramidOps.reshapeOutput(pyramidSrc,srcDerivY);

		PyramidOps.gradient(pyramidSrc, gradient, srcDerivX,srcDerivY);

		flowKlt.process(pyramidSrc,srcDerivX,srcDerivY,pyramidDst,flow);
	}

	@Override
	public ImageType<I> getInputType() {
		return imageType;
	}
}
