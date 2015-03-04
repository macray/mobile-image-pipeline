package com.tdb.mip.density;

import static org.truth0.Truth.ASSERT;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DensityUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void ratio_when_same_density_is_1() {
		Density source = new Density("", "", 1.2f);
		Density target = new Density("", "", 1.2f);

		float ratio = DensityUtils.getRatio(source, target);
		ASSERT.that(ratio).isEqualTo(1f);
	}

	@Test
	public void ratio_from_xxhdpi_to_mdpi() {
		Density source = AndroidDensity.XXHDPI;
		Density target = AndroidDensity.MDPI;
		float ratio = DensityUtils.getRatio(source, target);
		ASSERT.that(ratio).isEqualTo(1f / 3f);
	}

	@Test
	public void find_highest_density() {
		List<Density> densities = Arrays.asList( //
				AndroidDensity.MDPI, //
				AndroidDensity.XXHDPI, //
				AndroidDensity.LDPI //
				);
		
		Density highestDensity = DensityUtils.findHighestDensity(densities);
		ASSERT.that(highestDensity).isEqualTo(AndroidDensity.XXHDPI);
	}
	
	@Test
	public void find_closest_upper_density() {
		Density upper = DensityUtils.closestUpperDensity(AndroidDensity.ALL, AndroidDensity.XHDPI);
		ASSERT.that(upper).isEqualTo(AndroidDensity.XXHDPI);
	}
	
	@Test
	public void find_closest_upper_density_max_is_max() {
		Density upper = DensityUtils.closestUpperDensity(AndroidDensity.ALL, AndroidDensity.XXXHDPI);
		ASSERT.that(upper).isEqualTo(AndroidDensity.XXXHDPI);
	}
	
	@Test
	public void find_density_by_human_name() {
		ASSERT.that(DensityUtils.valueOf(AndroidDensity.ALL, "XHDPI")).isEqualTo(AndroidDensity.XHDPI);
		ASSERT.that(DensityUtils.valueOf(AndroidDensity.ALL, "xhdpi")).isEqualTo(AndroidDensity.XHDPI);
		ASSERT.that(DensityUtils.valueOf(AndroidDensity.ALL, "unknown")).isNull();
	}
}
