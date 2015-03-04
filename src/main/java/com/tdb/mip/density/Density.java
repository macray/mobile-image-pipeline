package com.tdb.mip.density;

// Create AndroidDensity, IOSDensity
public class Density {

	private String humanName;
	private String suffix;
	private float ratio;
	
	public Density(String humanName, String suffix, float ratio) {
		this.humanName = humanName;
		this.suffix = suffix;
		this.ratio = ratio;
	}

	public String getHumanName() {
		return humanName;
	}

	public String getSuffix() {
		return suffix;
	}

	public float getRatio() {
		return ratio;
	}
	
	
	
}
