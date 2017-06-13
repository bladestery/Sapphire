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

package boofcv.abst.tracker;

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
import boofcv.alg.tracker.sfot.SparseFlowObjectTracker;
import boofcv.alg.transform.fft.DiscreteFourierTransformOps;
import boofcv.alg.transform.wavelet.UtilWavelet;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.distort.FactoryDistort;
import boofcv.factory.filter.blur.FactoryBlurFilter;
import boofcv.factory.filter.kernel.FactoryKernelGaussian;
import boofcv.factory.transform.pyramid.FactoryPyramid;
import boofcv.struct.RectangleRotate_F32;
import boofcv.struct.RectangleRotate_F64;
import boofcv.struct.image.ImageGray;
import boofcv.struct.image.ImageType;
import georegression.geometry.UtilPoint2D_F64;
import georegression.struct.point.Point2D_F64;
import georegression.struct.shapes.Quadrilateral_F64;

/**
 * Wrapper around {@link SparseFlowObjectTracker} for {@link TrackerObjectQuad}.
 *
 * @author Peter Abeles
 */
public class Sfot_to_TrackObjectQuad<T extends ImageGray, D extends ImageGray>
		implements TrackerObjectQuad<T>
{
	SparseFlowObjectTracker<T,D> alg;

	RectangleRotate_F64 region = new RectangleRotate_F64();

	ImageType<T> type;

	public Sfot_to_TrackObjectQuad(SparseFlowObjectTracker<T, D> alg , Class<T> imageType, ImageType IT) {
		this.alg = alg;
		this.type = IT.single(imageType);
	}

	@Override
	public boolean initialize(T image, Quadrilateral_F64 location, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
							  FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
							  ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO,
							  GImageMiscOps GIMO, ImageMiscOps IMO, FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO,
							  GradientSobel_UnrolledOuter GSUO, FactoryPyramid FP, DiscreteFourierTransformOps DFTO, ImageType IT, FactoryDistort FDs) {
		quadToRectRot(location,region);

		alg.init(image,region, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW, FP, DHF, GSO, GSUO, IT, FDs);

		return true;
	}

	@Override
	public boolean process(T image, Quadrilateral_F64 location, GBlurImageOps GBIO, InputSanityCheck ISC, GeneralizedImageOps GIO, BlurImageOps BIO, ConvolveImageMean CIM,
						   FactoryKernelGaussian FKG, ConvolveNormalized CN, ConvolveNormalizedNaive CNN, ConvolveImageNoBorder CINB, ConvolveNormalized_JustBorder CNJB, ImplMedianHistogramInner IMHI,
						   ImplMedianSortEdgeNaive IMSEN, ImplMedianSortNaive IMSN, ImplConvolveMean ICM, GThresholdImageOps GTIO, GImageStatistics GIS, ImageStatistics IS, ThresholdImageOps TIO,
						   GImageMiscOps GIMO, ImageMiscOps IMO, FactoryBlurFilter FBF, ConvolveJustBorder_General CJBG, ConvertImage CI, UtilWavelet UW, DerivativeHelperFunctions DHF, GradientSobel_Outer GSO,
						   GradientSobel_UnrolledOuter GSUO, FactoryPyramid FP, DiscreteFourierTransformOps DFTO, ImageType IT, FactoryDistort FDs) {

		if( !alg.update(image,region, GBIO, ISC, GIO, BIO, CIM, FKG, CN, CNN, CINB, CNJB, IMHI, IMSEN, IMSN, ICM, GTIO, GIS, IS, TIO, GIMO, IMO, FBF, CJBG, CI, UW, DHF, GSO, GSUO, IT, FDs) )
			return false;

//		System.out.println("width "+region.width+" height "+region.height);

		rectRotToQuad(region, location);

		return true;
	}

	@Override
	public ImageType<T> getImageType() {
		return type;
	}

	public static void quadToRectRot( Quadrilateral_F64 q , RectangleRotate_F64 r ) {
		double centerX = (q.a.x + q.b.x + q.c.x + q.d.x)/4.0;
		double centerY = (q.a.y + q.b.y + q.c.y + q.d.y)/4.0;

		double topX = (q.a.x + q.b.x)/2.0;
		double topY = (q.a.y + q.b.y)/2.0;

		double sideX = (q.b.x + q.c.x)/2.0;
		double sideY = (q.b.y + q.c.y)/2.0;

		r.cx = centerX;
		r.cy = centerY;
		r.height = 2*UtilPoint2D_F64.distance(topX,topY,centerX,centerY);
		r.width = 2*UtilPoint2D_F64.distance(sideX,sideY,centerX,centerY);
		r.theta = Math.atan2( sideY - centerY , sideX - centerX );
	}

	public static void quadToRectRot( Quadrilateral_F64 q , RectangleRotate_F32 r ) {
		double centerX = (q.a.x + q.b.x + q.c.x + q.d.x)/4.0;
		double centerY = (q.a.y + q.b.y + q.c.y + q.d.y)/4.0;

		double topX = (q.a.x + q.b.x)/2.0;
		double topY = (q.a.y + q.b.y)/2.0;

		double sideX = (q.b.x + q.c.x)/2.0;
		double sideY = (q.b.y + q.c.y)/2.0;

		r.cx = (float)centerX;
		r.cy = (float)centerY;
		r.height = 2*(float)UtilPoint2D_F64.distance(topX,topY,centerX,centerY);
		r.width = 2*(float)UtilPoint2D_F64.distance(sideX,sideY,centerX,centerY);
		r.theta = (float)Math.atan2( sideY - centerY , sideX - centerX );
	}

	public static void rectRotToQuad( RectangleRotate_F64 r , Quadrilateral_F64 q ) {

		double c = Math.cos(r.theta);
		double s = Math.sin(r.theta);

		setPoint(q.a,-r.width/2, -r.height/2.0,c,s);
		setPoint(q.b, r.width/2, -r.height/2.0,c,s);
		setPoint(q.c, r.width/2,  r.height/2.0,c,s);
		setPoint(q.d,-r.width/2,  r.height/2.0,c,s);

		q.a.x += r.cx;
		q.a.y += r.cy;

		q.b.x += r.cx;
		q.b.y += r.cy;

		q.c.x += r.cx;
		q.c.y += r.cy;

		q.d.x += r.cx;
		q.d.y += r.cy;
	}

	public static void rectRotToQuad( RectangleRotate_F32 r , Quadrilateral_F64 q ) {

		double c = Math.cos(r.theta);
		double s = Math.sin(r.theta);

		setPoint(q.a,-r.width/2, -r.height/2.0,c,s);
		setPoint(q.b, r.width/2, -r.height/2.0,c,s);
		setPoint(q.c, r.width/2,  r.height/2.0,c,s);
		setPoint(q.d,-r.width/2,  r.height/2.0,c,s);

		q.a.x += r.cx;
		q.a.y += r.cy;

		q.b.x += r.cx;
		q.b.y += r.cy;

		q.c.x += r.cx;
		q.c.y += r.cy;

		q.d.x += r.cx;
		q.d.y += r.cy;
	}

	private static void setPoint( Point2D_F64 p , double x , double y , double c , double s ) {
		p.x = x*c - y*s;
		p.y = x*s + y*c;
	}
}
