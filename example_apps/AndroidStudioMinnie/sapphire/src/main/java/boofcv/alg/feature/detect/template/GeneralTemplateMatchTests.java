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

package boofcv.alg.feature.detect.template;

import boofcv.abst.feature.detect.extract.ConfigExtract;
import boofcv.abst.feature.detect.extract.NonMaxSuppression;
import boofcv.alg.misc.GImageMiscOps;
import boofcv.alg.misc.ImageMiscOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.GeneralizedImageOps;
import boofcv.factory.feature.detect.extract.FactoryFeatureExtractor;
import boofcv.struct.QueueCorner;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageGray;
import boofcv.testing.BoofTesting;
import georegression.geometry.UtilPoint2D_F64;
import georegression.struct.point.Point2D_I16;
import georegression.struct.point.Point2D_I32;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Peter Abeles
 */
@SuppressWarnings("unchecked")
public abstract class GeneralTemplateMatchTests<T extends ImageGray> {
	private ImageStatistics IS;
	private GeneralizedImageOps GIO;
	private static FactoryFeatureExtractor FFE;
	private static GImageMiscOps GIMO;
	private static ImageMiscOps IMO;
	Random rand = new Random(344);

	// image and template being matched
	T image;
	T template;
	T mask;

	// algorithm being evaluated
	TemplateMatchingIntensity<T> alg;

	// if a perfect match is zero additional tests can be done
	boolean isPerfectZero;

	public GeneralTemplateMatchTests(TemplateMatchingIntensity<T> alg, Class<T> imageType) {
		this.alg = alg;

		image = GIO.createSingleBand(imageType, 30, 40);
		template = GIO.createSingleBand(imageType, 5, 8);
		mask = GIO.createSingleBand(imageType, 5, 8);

		GIMO.fillUniform(template, rand, 50, 60, IMO);
	}

	public void allTests() {
		border_nomask();
		negativeCase_nomask();
		negativeCase_Mask();
		singleCase();
		multipleCases();
		subImage();
		zeroMask();
		maskDifferentiate();
	}

	/**
	 * Makes sure the template border is calculated correctly
	 */
	@Test
	public void border_nomask() {
		alg.setInputImage(image);
		alg.process(template);

		assertEquals(template.width/2,alg.getBorderX0());
		assertEquals(template.height/2,alg.getBorderY0());
		assertEquals(template.width-template.width/2,alg.getBorderX1());
		assertEquals(template.height-template.height/2,alg.getBorderY1());
	}

	@Test
	public void border_Mask() {
		alg.setInputImage(image);
		alg.process(template, mask);

		assertEquals(template.width/2,alg.getBorderX0());
		assertEquals(template.height/2,alg.getBorderY0());
		assertEquals(template.width-template.width/2,alg.getBorderX1());
		assertEquals(template.height-template.height/2,alg.getBorderY1());
	}

	@Test
	public void negativeCase_nomask() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);

		alg.setInputImage(image);
		alg.process(template);

		if (isPerfectZero) {
			// there should be no perfect matches
			int x0 = alg.getBorderX0();
			int x1 = image.width - (template.width - x0);
			int y0 = alg.getBorderY0();
			int y1 = image.width - (template.width - x0);

			GrayF32 intensity = alg.getIntensity();

			for (int y = y0; y < y1; y++) {
				for (int x = x0; x < x1; x++) {
					if (intensity.get(x, y) == 0)
						fail("There should be no perfect matches");
				}
			}
		}
	}

	@Test
	public void negativeCase_Mask() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);
		GIMO.fill(mask,1, IMO);

		alg.setInputImage(image);
		alg.process(template,mask);

		if (isPerfectZero) {
			// there should be no perfect matches
			int x0 = alg.getBorderX0();
			int x1 = image.width - (template.width - x0);
			int y0 = alg.getBorderY0();
			int y1 = image.width - (template.width - x0);

			GrayF32 intensity = alg.getIntensity();

			for (int y = y0; y < y1; y++) {
				for (int x = x0; x < x1; x++) {
					if (intensity.get(x, y) == 0)
						fail("There should be no perfect matches");
				}
			}
		}
	}

	/**
	 * There is only a single match
	 */
	@Test
	public void singleCase() {
		GIMO.fill(image,0, IMO);
//		GIMO.fillUniform(image, rand, 0, 50);

		int locationX = 10;
		int locationY = 12;

		setTemplate(locationX, locationY);

		alg.setInputImage(image);
		alg.process(template);
		checkExpected(new Point2D_I32(locationX, locationY));

		// uniform mask should produce identical results
		GIMO.fill(mask,1, IMO);
		alg.process(template, mask);
		checkExpected(new Point2D_I32(locationX, locationY));
	}

	/**
	 * The mask is filled with zeros and even though there is a perfect match it shouldn't be found
	 */
	@Test
	public void zeroMask() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);

		int locationX = 10;
		int locationY = 12;

		setTemplate(locationX, locationY);

		GIMO.fill(mask,0, IMO);
		GIMO.fill(alg.getIntensity(),0, IMO);
		alg.setInputImage(image);
		alg.process(template, mask);
		assertEquals(0, IS.maxAbs(alg.getIntensity()),1e-4f);
	}

	/**
	 * Provide two matches
	 */
	@Test
	public void multipleCases() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);

		Point2D_I32 a = new Point2D_I32(10, 12);
		Point2D_I32 b = new Point2D_I32(20, 16);

		setTemplate(a.x, a.y);
		setTemplate(b.x, b.y);

		alg.setInputImage(image);
		alg.process(template);
		checkExpected(a, b);

		// uniform mask should produce identical results
		GIMO.fill(mask,1, IMO);
		alg.process(template, mask);
		checkExpected(a, b);
	}

	/**
	 * If the mask is correctly applied then two matches will be found inside the image.  Otherwise just one.
	 */
	@Test
	public void maskDifferentiate() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);

		T template = (T)image.createNew(12,12);
		GIMO.fillUniform(template, rand, 0, 200, IMO);

		int x = 10, y = 12;
		template.subimage(3,3,9,9,null).setTo(image.subimage(x-3,y-3,x+3,y+3,null));
		alg.setInputImage(image);
		alg.process(template);

		float valueNoMask = fractionBest(alg.getIntensity(),x,y);
		float averageNoMask = fractionAverage(alg.getIntensity(),x,y);

		T mask = (T)image.createNew(12,12);
		GIMO.fill(mask,0, IMO);
		GIMO.fill(mask.subimage(3,3,9, 9, null),1, IMO);

		alg.setInputImage(image);
		alg.process(template,mask);

		float valueMask = fractionBest(alg.getIntensity(), x, y);
		float averageMask = fractionAverage(alg.getIntensity(), x, y);

		// this score is designed to reduce the affect of the change in template "size"
		float scoreNoMask = valueNoMask/averageNoMask;
		float scoreMask = valueMask/averageMask;

		assertTrue(valueMask >= valueNoMask );
		// when masked it should be better at differentiating it from background noise
		assertTrue(scoreMask*0.9 > scoreNoMask );
	}

	public float fractionBest(GrayF32 intensity , int x , int y ) {
		float min = IS.min(intensity);
		float max = IS.max(intensity);

		float value = intensity.get(x,y);

		return (value-min)/(max-min);
	}

	public float fractionAverage(GrayF32 intensity , int x , int y ) {
		float min = IS.min(intensity);
		float max = IS.max(intensity);
		float average = (float)IS.mean(intensity);

		return (average-min)/(max-min);
	}

	/**
	 * Provide inputs which are subimages and see if it produces the correct results
	 */
	@Test
	public void subImage() {
		GIMO.fillUniform(image, rand, 0, 200, IMO);

		Point2D_I32 a = new Point2D_I32(10, 12);
		Point2D_I32 b = new Point2D_I32(20, 16);

		setTemplate(a.x, a.y);
		setTemplate(b.x, b.y);

		T subImage = BoofTesting.createSubImageOf(image);
		T subTemplate = BoofTesting.createSubImageOf(template);

		alg.setInputImage(subImage);
		alg.process(subTemplate);
		checkExpected(a, b);

		// uniform mask should produce identical results
		T subMask = BoofTesting.createSubImageOf(mask);
		GIMO.fill(subMask,1, IMO);
		alg.setInputImage(subImage);
		alg.process(subTemplate,subMask);
		checkExpected(a, b);
	}

	private void checkExpected(Point2D_I32... points) {
		// I'm being lazy, update this in the future
		assertFalse(alg.isBorderProcessed());

		// only process the regions which are not considered the border
		int x0 = alg.getBorderX0();
		int y0 = alg.getBorderY0();

		// solutions should be local maximums
		NonMaxSuppression extractor = FFE.nonmax(new ConfigExtract(2, -Float.MAX_VALUE, 0, true));

		QueueCorner found = new QueueCorner(10);

		extractor.process(alg.getIntensity(), null,null,null, found);

		assertTrue(found.size >= points.length);

		// search for all the expected matches
		for (Point2D_I32 expected : points) {
			int numMatches = 0;

			for (Point2D_I16 f : found.toList()) {
				double d = UtilPoint2D_F64.distance(f.x-x0,f.y-y0,expected.x,expected.y);
				if (d <= 1)
					numMatches++;
			}

			assertEquals(1, numMatches);
		}
	}

	private void setTemplate(int x, int y) {
		image.subimage(x, y, x + template.width, y + template.height, null).setTo(template);
	}
}
