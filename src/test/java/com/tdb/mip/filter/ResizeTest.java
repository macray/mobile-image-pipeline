package com.tdb.mip.filter;

import static org.truth0.Truth.ASSERT;

import javax.xml.ws.ServiceMode;

import org.junit.Before;
import org.junit.Test;

import com.tdb.mip.util.PixelRoundingHalfDown;

public class ResizeTest {

	private PixelRoundingHalfDown pixelRounding;
	
	@Before
	public void setUp() {
		pixelRounding = new PixelRoundingHalfDown();
	}
	
	@Test
	public void compute_missing_width_and_keep_proportion_h140() {
		Resize resize = new Resize(-1, 140, pixelRounding);
		resize.updateTargetWidthOrHeightIfNeeded(100, 280);
		ASSERT.that(resize.getH()).isEqualTo(140);
		ASSERT.that(resize.getW()).isEqualTo(50);
	}

	@Test
	public void compute_missing_height_and_keep_proportion_w140() {
		Resize resize = new Resize(140, -1, pixelRounding);
		resize.updateTargetWidthOrHeightIfNeeded(280, 100);
		ASSERT.that(resize.getH()).isEqualTo(50);
		ASSERT.that(resize.getW()).isEqualTo(140);
	}

	@Test
	public void compute_missing_width_and_keep_proportion_h150() {
		Resize resize = new Resize(-1, 150, pixelRounding);
		resize.updateTargetWidthOrHeightIfNeeded(300, 96);
		ASSERT.that(resize.getH()).isEqualTo(150);
		ASSERT.that(resize.getW()).isEqualTo(469); // 468.75
	}

	@Test
	public void compute_missing_height_and_keep_proportion_w150() {
		Resize resize = new Resize(150, -1, pixelRounding);
		resize.updateTargetWidthOrHeightIfNeeded(272, 200);
		ASSERT.that(resize.getH()).isEqualTo(110);
		ASSERT.that(resize.getW()).isEqualTo(150);
	}

	@Test
	public void compute_missing_height_and_keep_proportion_w150_2() {
		Resize resize = new Resize(150, -1, pixelRounding);
		resize.updateTargetWidthOrHeightIfNeeded(200, 233);
		ASSERT.that(resize.getH()).isEqualTo(175);
		ASSERT.that(resize.getW()).isEqualTo(150);
	}

}
