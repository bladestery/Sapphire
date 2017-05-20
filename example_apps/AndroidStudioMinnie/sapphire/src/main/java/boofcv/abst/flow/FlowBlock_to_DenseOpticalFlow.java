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
import boofcv.alg.flow.DenseOpticalFlowBlockPyramid;
import boofcv.alg.flow.UtilDenseOpticalFlow;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
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

/**
 * Wrapper around {@link boofcv.alg.flow.DenseOpticalFlowBlockPyramid} for {@link boofcv.abst.flow.DenseOpticalFlow}.
 *
 * @author Peter Abeles
 */
public class FlowBlock_to_DenseOpticalFlow<T extends ImageGray>
	implements DenseOpticalFlow<T>
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
	private static ImageType IT;
	private static UtilWavelet UW;
	private static ConvertImage CI;
	DenseOpticalFlowBlockPyramid<T> flowAlg;

	// width and height of input image.  used to see if anything changes
	int width = -1;
	int height = -1;

	// relative change in scale between pyramid layers
	double scale;
	// maximum number of layers in the pyramid
	int maxLayers;

	ImagePyramid<T> pyramidSrc;
	ImagePyramid<T> pyramidDst;

	ImageType<T> imageType;

	public FlowBlock_to_DenseOpticalFlow(DenseOpticalFlowBlockPyramid<T> flowAlg,
										 double scale,
										 int maxLayers,
										 Class<T> imageType) {
		this.flowAlg = flowAlg;
		this.scale = scale;
		this.maxLayers = maxLayers;

		this.imageType = IT.single(imageType);
	}

	@Override
	public void process(T source, T destination, ImageFlow flow) {

		if( width != source.width || height != source.height ) {
			width = source.width;
			height = source.height;

			int minSize = (2*(flowAlg.getRegionRadius() + flowAlg.getRegionRadius()+1) + 1);

			// apply no blur to the layers.  If the user wants a blurred image they can blur it themselves
			pyramidSrc = UtilDenseOpticalFlow.standardPyramid(source.width,source.height,scale,0,
					minSize,maxLayers,source.getImageType().getImageClass());
			pyramidDst = UtilDenseOpticalFlow.standardPyramid(source.width,source.height,scale,0,
					minSize,maxLayers,source.getImageType().getImageClass());
		}

		pyramidSrc.process(source, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW);
		pyramidDst.process(destination, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW);

		flowAlg.process(pyramidSrc,pyramidDst);

		flow.setTo(flowAlg.getOpticalFlow());
	}

	@Override
	public ImageType<T> getInputType() {
		return imageType;
	}
}
