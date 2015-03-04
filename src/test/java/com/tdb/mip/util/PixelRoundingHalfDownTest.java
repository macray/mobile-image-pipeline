package com.tdb.mip.util;

import static org.truth0.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;

public class PixelRoundingHalfDownTest {

	private PixelRounding rounding;
	
	@Before
	public void setUp() throws Exception {
		rounding = new PixelRoundingHalfDown();
	}
	
	@Test
	public void should_round_down() {
		ASSERT.that(rounding.round(1.0f)).isEqualTo(1);
		ASSERT.that(rounding.round(1.2f)).isEqualTo(1);
		ASSERT.that(rounding.round(1.5f)).isEqualTo(1); // different result than RoundingUp
		ASSERT.that(rounding.round(1.6f)).isEqualTo(2);
	}

}
