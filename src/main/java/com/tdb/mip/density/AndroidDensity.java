package com.tdb.mip.density;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndroidDensity {

	public static Density LDPI = new Density("ldpi", "ldpi", 0.75f);
	public static Density MDPI = new Density("mdpi", "mdpi", 1f);
	public static Density HDPI = new Density("hdpi", "hdpi", 1.5f);
	public static Density XHDPI = new Density("xhdpi", "xhdpi", 2f);
	public static Density XXHDPI = new Density("xxhdpi", "xxhdpi", 3f);
	public static Density XXXHDPI = new Density("xxxhdpi", "xxxhdpi", 4f);

	public static List<Density> ALL = Collections.unmodifiableList( //
			Arrays.asList(LDPI, MDPI, HDPI, XHDPI, XXHDPI, XXXHDPI) //
			);

}
