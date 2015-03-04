package com.tdb.mip.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class PixelRoundingHalfDown implements PixelRounding {

	private NumberFormat numberFormat;

	public PixelRoundingHalfDown() {
		numberFormat = NumberFormat.getNumberInstance(Locale.US);
		numberFormat.setMaximumFractionDigits(0);
		numberFormat.setRoundingMode(RoundingMode.HALF_DOWN);
	}

	@Override
	public int round(float value) {
		return Integer.valueOf(numberFormat.format(value));
	}

}
