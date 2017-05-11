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

import boofcv.alg.InputSanityCheck;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import georegression.struct.point.Point2D_I32;

import java.util.List;

/**
 * Wrapper that converts an input image data type into a different one
 *
 * @author Peter Abeles
 */
public class DescribeImageDense_Convert<T extends ImageBase, Desc extends TupleDesc>
		implements DescribeImageDense<T,Desc>
{
	private static InputSanityCheck ISC;
	private static GImageMiscOps GIMO;
	private static GeneralizedImageOps GIO;
	private static ImageMiscOps IMO;
	DescribeImageDense<ImageBase,Desc> describer;
	ImageBase workspace;

	ImageType<T> inputType;

	public DescribeImageDense_Convert(DescribeImageDense<ImageBase,Desc> describer , ImageType<T> inputType )
	{
		ImageType describerType = describer.getImageType();

		if( inputType.getFamily() != describerType.getFamily() )
			throw new IllegalArgumentException("Image types must have the same family");
		if( inputType.getDataType() == describerType.getDataType() )
			throw new IllegalArgumentException("Data types are the same.  Why do you want to use this class?");

		workspace = describerType.createImage(1,1);
		this.describer = describer;
		this.inputType = inputType;
	}


	@Override
	public Desc createDescription() {
		return describer.createDescription();
	}

	@Override
	public Class<Desc> getDescriptionType() {
		return describer.getDescriptionType();
	}

	@Override
	public void process(T input) {
		workspace.reshape(input.width,input.height);
		GConvertImage.convert(input,workspace, ISC, GIO, GIMO, IMO);
		describer.process(workspace);
	}

	@Override
	public List<Desc> getDescriptions() {
		return describer.getDescriptions();
	}

	@Override
	public List<Point2D_I32> getLocations() {
		return describer.getLocations();
	}

	@Override
	public ImageType<T> getImageType() {
		return inputType;
	}
}
