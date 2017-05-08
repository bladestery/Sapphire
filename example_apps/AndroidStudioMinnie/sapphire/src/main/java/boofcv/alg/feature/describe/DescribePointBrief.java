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

package boofcv.alg.feature.describe;

import boofcv.abst.filter.blur.BlurFilter;
import boofcv.alg.InputSanityCheck;
import boofcv.alg.feature.describe.brief.BinaryCompareDefinition_I32;
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
import boofcv.alg.filter.convolve.noborder.ImplConvolveMean;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;

/**
 * <p>
 * BRIEF: Binary Robust Independent Elementary Features. [1] Invariance: light.  Fast to compute
 * and to compare feature descriptions.  A variant on {@link DescribePointBinaryCompare} where the descriptor
 * is computed from a single, randomly generated {@link BinaryCompareDefinition_I32 definition} after the image
 * has been blurred.
 * </p>
 *
 * <p>
 * [1] Michael Calonder, Vincent Lepetit, Christoph Strecha, and Pascal Fua. "BRIEF: Binary Robust Independent Elementary
 * Features" in European Conference on Computer Vision, September 2010.
 * </p>
 *
 * @author Peter Abeles
 */
public class DescribePointBrief<T extends ImageGray> {
	private static GeneralizedImageOps GIO;
	private static ImageType IT;
	private static InputSanityCheck ISC;
	private static GBlurImageOps GBIO;
	private static BlurImageOps BIO;
	private static ConvolveImageMean CIM;
	private static FactoryKernelGaussian FKG;
	private static ConvolveNormalized CN;
	private static ConvolveNormalizedNaive CNN;
	private static ConvolveImageNoBorder CINB;
	private static ConvolveNormalized_JustBorder CNJB;
	private static ImplMedianHistogramInner IMHI;
	private static ImplMedianSortEdgeNaive IMSEN;
	private static ImplMedianSortNaive IMSN;
	private static ImplConvolveMean ICM;
	private static GThresholdImageOps GTIO;
	private static GImageStatistics GIS;
	private static ImageStatistics IS;
	private static ThresholdImageOps TIO;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;

	// blurs the image prior to sampling
	protected BlurFilter<T> filterBlur;
	// blurred image
	protected T blur;

	// computes the binary feature description
	protected DescribePointBinaryCompare<T> describe;

	public DescribePointBrief(DescribePointBinaryCompare<T> describe, BlurFilter<T> filterBlur) {
		this.filterBlur = filterBlur;
		this.describe = describe;

		Class<T> imageType = filterBlur.getInputType(IT).getImageClass();
		blur = GIO.createSingleBand(imageType, 1, 1);
	}

	/**
	 * Function which creates a description of the appropriate size.
	 *
	 * @return Creates a bew description.
	 */
	public TupleDesc_B createFeature() {
		return new TupleDesc_B(describe.getDefinition().getLength());
	}

	/**
	 * Specifies the image from which feature descriptions are to be created.
	 *
	 * @param image Image being examined.
	 */
	public void setImage(T image) {
		blur.reshape(image.width,image.height);
		filterBlur.process(image,blur, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO);
		describe.setImage(image);
	}

	/**
	 * Computes the descriptor at the specified point.  If the region go outside of the image then a description
	 * will not be made.
	 *
	 * @param c_x Center of region being described.
	 * @param c_y Center of region being described.
	 * @param feature Where the descriptor is written to.
	 */
	public void process( double c_x , double c_y , TupleDesc_B feature ) {
		describe.process((int)c_x,(int)c_y,feature);
	}

	public BinaryCompareDefinition_I32 getDefinition() {
		return describe.getDefinition();
	}
}
