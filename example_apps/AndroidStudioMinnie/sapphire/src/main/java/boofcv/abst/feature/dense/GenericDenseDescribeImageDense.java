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

package boofcv.abst.feature.dense;

import boofcv.abst.feature.describe.DescribeRegionPoint;
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
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.GImageStatistics;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.core.image.border.FactoryImageBorder;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.interpolate.FactoryInterpolation;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_I32;
import sapphire.compiler.GIMOGenerator;
import sapphire.compiler.GIOGenerator;

import org.ddogleg.struct.FastQueue;

import java.util.List;

/**
 * Dense feature computation which uses {@link boofcv.abst.feature.describe.DescribeRegionPoint} internally.
 *
 * @author Peter Abeles
 */
public class GenericDenseDescribeImageDense<T extends ImageBase, Desc extends TupleDesc>
	implements DescribeImageDense<T,Desc>
{
	// Computes the image feature
	DescribeRegionPoint<T,Desc> alg;

	// conversion from scale to feature radius
	double scaleToRadius;
	// Radius of the "detected" feature
	double radius;
	// The width of the area the feature will sample
	int featureWidth;
	// the period at which it will be sampled inside the image
	int periodX;
	int periodY;

	FastQueue<Desc> descriptions;
	FastQueue<Point2D_I32> locations = new FastQueue<>(Point2D_I32.class, true);

	/**
	 * Configures dense description.
	 * @param alg Sparse feature sampler.
	 * @param scaleToRadius Conversion between requested descriptor scale to its actual radius
	 * @param descriptorScale Relative scale of the descriptor's region
	 * @param samplePeriodX How frequently the image is sampled in pixels. X-axis
	 * @param samplePeriodY How frequently the image is sampled in pixels. Y-axis
	 */
	public GenericDenseDescribeImageDense(DescribeRegionPoint<T, Desc> alg,
										  double scaleToRadius,
										  double descriptorScale,
										  double samplePeriodX ,
										  double samplePeriodY ) {
		this.scaleToRadius = scaleToRadius;
		this.alg = alg;

		configure(descriptorScale,samplePeriodX,samplePeriodY);
	}

	/**
	 * Configures size of the descriptor and the frequency at which it's computed
	 *
	 * @param descriptorRegionScale Relative size of the descriptor region to its canonical size
	 * @param periodX Period in pixels along x-axis of samples
	 * @param periodY Period in pixels along y-axis of samples
	 */
	public void configure( double descriptorRegionScale , double periodX, double periodY) {
		this.radius = descriptorRegionScale*scaleToRadius;
		this.periodX = (int)(periodX+0.5);
		this.periodY = (int)(periodY+0.5);
		this.featureWidth = (int)(alg.getCanonicalWidth()*descriptorRegionScale + 0.5);

		descriptions = new FastQueue<Desc>(alg.getDescriptionType(),true) {
			@Override
			protected Desc createInstance() {
				return alg.createDescription();
			}
		};
	}

	@Override
	public void process(T input, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM, FactoryKernelGaussian FKG, ConvolveNormalized CN,
						ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI, ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN,
						ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO, GImageMiscOps GIMO, ImageMiscOps IMO, ConvolveJustBorder_General CJBG,
						ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO, GradientSobel_UnrolledOuter GSUO, FactoryImageBorder FIB, ImageType IT, FactoryInterpolation FI) {
		if( periodX <= 0 || periodY <= 0 )
			throw new IllegalArgumentException("Must call configure() first");

		alg.setImage(input, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, CJBG, CI, UW, DHF, GSO, GSUO, FIB, IT, FI);

		int x0 = featureWidth/2;
		int x1 = input.getWidth()-featureWidth/2;
		int y0 = featureWidth/2;
		int y1 = input.getHeight()-featureWidth/2;

		descriptions.reset();
		locations.reset();

		for (int y = y0; y < y1; y += periodY ) {
			for (int x = x0; x < x1; x += periodX ) {
				Desc d = descriptions.grow();

				if( !alg.process(x,y,0,radius,d) ) {
					descriptions.removeTail();
				} else {
					locations.grow().set(x,y);
				}
			}
		}
	}

	@Override
	public List<Desc> getDescriptions() {
		return descriptions.toList();
	}

	@Override
	public List<Point2D_I32> getLocations() {
		return locations.toList();
	}

	@Override
	public ImageType<T> getImageType() {
		return alg.getImageType();
	}

	@Override
	public Desc createDescription() {
		return alg.createDescription();
	}

	@Override
	public Class<Desc> getDescriptionType() {
		return alg.getDescriptionType();
	}
}
