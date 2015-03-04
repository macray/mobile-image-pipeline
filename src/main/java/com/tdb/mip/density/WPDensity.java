package com.tdb.mip.density;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WPDensity {

	public static Density _1 = new Density("1", "", 1.0f); // WP + Windows Store
	public static Density _1_4 = new Density("1.4", "@2x", 1.4f); // WP + Windows Store
	public static Density _1_8 = new Density("1.8", "@3x", 1.8f); // Windows Store
	public static Density _2_4 = new Density("2.4", "@3x", 2.4f); // WP

	public static List<Density> WINDOWS_PHONE = Collections.unmodifiableList( //
			Arrays.asList(_1, _1_4, _2_4) //
			);


	/*
	 * WVGA
480 × 800
15:9
None. This is the only supported resolution for Windows Phone OS 7.1.
480 × 800
WXGA
768 × 1280
15:9
1.6x scale
480 × 800
720p
720 × 1280
16:9
1.5x scale, 80 pixels taller (53 pixels, after scaling)
480 × 853
1080p
1080 x 1920
16:9
1.5x scale, 80 pixels taller (53 pixels, after scaling)
480 × 853

	 */
}
