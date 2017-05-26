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

package boofcv.abst.segmentation;

import boofcv.alg.InputSanityCheck;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.segmentation.ComputeRegionMeanColor;
import boofcv.alg.segmentation.ImageSegmentationOps;
import boofcv.alg.segmentation.ms.MergeSmallRegions;
import boofcv.alg.segmentation.watershed.WatershedVincentSoille1991;
import boofcv.core.image.ConvertImage;
import boofcv.core.image.GConvertImage;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.struct.ConnectRule;
import boofcv.struct.feature.ColorQueue_F32;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.ImageBase;
import boofcv.struct.image.ImageType;
import sapphire.compiler.IMOGenerator;

import org.ddogleg.struct.FastQueue;
import org.ddogleg.struct.GrowQueue_I32;

import javax.print.attribute.standard.MediaSize;

/**
 * Wrapper around {@link WatershedVincentSoille1991} for {@link ImageSuperpixels}.  Watershed regions
 * and small regions are merged together.  When merging regions a preference is given to regions which are the
 * most similar in color intensity.
 *
 * @author Peter Abeles
 */
public class Watershed_to_ImageSuperpixels<T extends ImageBase> implements ImageSuperpixels<T> {
	private WatershedVincentSoille1991 alg;
	private ConnectRule rule;

	private GrayU8 converted = new GrayU8(1,1);

	private MergeSmallRegions<GrayU8> pruneSmall;

	private FastQueue<Integer> regionMemberCount = new FastQueue<>(Integer.class,false);
	private FastQueue<float[]> regionColor = new ColorQueue_F32(1);

	private int numRegions;

	// this isn't really needed since image type is determined when segment is called
	// but is required by the interface
	private ImageType<T> imageType;

	public Watershed_to_ImageSuperpixels(WatershedVincentSoille1991 alg, int minimumSize, ConnectRule rule) {
		this.alg = alg;
		this.rule = rule;

		if( minimumSize > 0 )
			pruneSmall = new MergeSmallRegions<>(minimumSize,rule,new ComputeRegionMeanColor.U8());
	}

	@Override
	public void segment(T input, GrayS32 output, InputSanityCheck ISC, GeneralizedImageOps GIO, GImageMiscOps GIMO, ImageMiscOps IMO, ImageSegmentationOps ISO, BinaryImageOps BIO,
					   ConvertImage CI, ImageType IT) {
		ISC.checkSameShape(input,output);
		converted.reshape(input.width,input.height);

		GConvertImage.convert(input,converted, ISC, GIO, GIMO, IMO, CI, IT);

		// segment the image
		alg.process(converted, IMO);
		alg.removeWatersheds();

		numRegions = alg.getTotalRegions();
		GrayS32 pixelToRegion = alg.getOutput();

		// Merge small regions together
		if( pruneSmall != null ) {
			regionMemberCount.resize(numRegions);
			regionColor.resize(numRegions);

			ISO.countRegionPixels(pixelToRegion,numRegions,regionMemberCount.data);
			pruneSmall.process(converted,pixelToRegion,regionMemberCount,regionColor, BIO);

			numRegions = regionMemberCount.size();
		}

		output.setTo(pixelToRegion);
	}

	@Override
	public int getTotalSuperpixels() {
		return numRegions;
	}

	@Override
	public ConnectRule getRule() {
		return rule;
	}

	public ImageType<T> getImageType(ImageType IT) {
		return imageType;
	}

	public void setImageType(ImageType<T> imageType) {
		this.imageType = imageType;
	}
}
