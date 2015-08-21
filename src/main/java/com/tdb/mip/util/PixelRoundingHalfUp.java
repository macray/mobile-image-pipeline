package com.tdb.mip.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class PixelRoundingHalfUp implements PixelRounding {

	private NumberFormat numberFormat;

	public PixelRoundingHalfUp() {
		numberFormat = NumberFormat.getNumberInstance(Locale.US);
		numberFormat.setMaximumFractionDigits(0);
		numberFormat.setGroupingUsed(false);
		numberFormat.setRoundingMode(RoundingMode.HALF_UP);
	}

	@Override
	public int round(float value) {
		return Integer.valueOf(numberFormat.format(value));
	}

}
