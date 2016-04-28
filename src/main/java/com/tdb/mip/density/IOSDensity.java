package com.tdb.mip.density;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IOSDensity {
	public static Density X1 = new Density("1x", "", 1f);
	public static Density X2 = new Density("2x", "@2x", 2f);
	public static Density X3 = new Density("3x", "@3x", 3f);

	public static List<Density> ALL = Collections.unmodifiableList( //
			Arrays.asList(X1, X2, X3) //
			);
}
