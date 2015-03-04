package com.tdb.mip.density;

import java.util.LinkedList;
import java.util.List;

public class DensityUtils {

	public static float getRatio(Density source, Density target) {
		return target.getRatio() / source.getRatio();
	}
	
	public static Density valueOf(List<Density> allDensitiesForPlatform, String densityHumanName) {
		for(Density d : allDensitiesForPlatform) {
			if(d.getHumanName().equalsIgnoreCase(densityHumanName)) {
				return d;
			}
		}
		
		return null;
	}

	public static Density findHighestDensity(List<Density> densities) {
		Density highestDensity = densities.get(0);
		for (Density density : densities) {
			if (density.getRatio() > highestDensity.getRatio()) {
				highestDensity = density;
			}
		}

		return highestDensity;
	}

	public static Density closestUpperDensity(List<Density> allDensitiesForPlatform, Density refDensity) {
		// make a sub list with all the higher densities
		List<Density> higherDensity = new LinkedList<>();
		for (Density d : allDensitiesForPlatform) {
			if (d.getRatio() > refDensity.getRatio()) {
				higherDensity.add(d);
			}
		}

		if (!higherDensity.isEmpty()) {
			// if not empty find the min in the sub list
			Density min = higherDensity.get(0);
			for(Density d : higherDensity) {
				if(min.getRatio() > d.getRatio()) {
					min = d;
				}
			}
			return min;
		} else {
			// if empty return the density itself
			return refDensity;
		}

	}

}
