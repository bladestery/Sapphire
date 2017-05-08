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

package boofcv.abst.feature.detdesc;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.convolve.ConvolveImageNoBorder;
import boofcv.alg.filter.convolve.ConvolveNormalized;
import boofcv.alg.filter.convolve.border.ConvolveJustBorder_General;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalizedNaive;
import boofcv.alg.filter.convolve.normalized.ConvolveNormalized_JustBorder;
import boofcv.alg.filter.derivative.DerivativeHelperFunctions;
import boofcv.alg.filter.derivative.impl.GradientSobel_Outer;
import boofcv.alg.filter.derivative.impl.GradientSobel_UnrolledOuter;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageGray;
import georegression.struct.point.Point2D_F64;

/**
 * Allows {@link DetectDescribePoint} to be used as a {@link DetectDescribeMulti}.
 *
 * @author Peter Abeles
 */
public class DetectDescribeSingleToMulti<T extends ImageGray, TD extends TupleDesc>
	implements DetectDescribeMulti<T,TD>
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
	DetectDescribePoint<T,TD> alg;
	Wrap set = new Wrap();

	public DetectDescribeSingleToMulti(DetectDescribePoint<T, TD> alg) {
		this.alg = alg;
	}

	@Override
	public void process(T image) {
		alg.detect(image, ISC, DHF, CINB, CJBG, GSO, GSUO, GIMO, IMO, CNN, CNJB, CN);
	}

	@Override
	public int getNumberOfSets() {
		return 1;
	}

	@Override
	public PointDescSet<TD> getFeatureSet(int setIndex) {
		if( setIndex != 0 )
			throw new IllegalArgumentException("Only one set");
		return set;
	}

	@Override
	public TD createDescription() {
		return alg.createDescription();
	}

	@Override
	public Class<TD> getDescriptionType() {
		return alg.getDescriptionType();
	}

	private class Wrap implements PointDescSet<TD> {

		@Override
		public int getNumberOfFeatures() {
			return alg.getNumberOfFeatures();
		}

		@Override
		public Point2D_F64 getLocation(int featureIndex) {
			return alg.getLocation(featureIndex);
		}

		@Override
		public TD getDescription(int index) {
			return alg.getDescription(index);
		}
	}
}
