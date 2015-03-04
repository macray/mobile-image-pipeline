package com.tdb.mip.filter;

import static org.truth0.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;


public class ResizeFactoryTest extends BaseFilterFactoryTest{

	private ResizeFactory factory;

	@Before
	public void setUp() {
		factory = new ResizeFactory();
	}

	@Test
	public void filter_detects_his_config_properly() {
		// detect valid configuration
		ASSERT.that(factory.canBuild(filter("h320"))).isTrue();
		ASSERT.that(factory.canBuild(filter("h320w300"))).isTrue();
		ASSERT.that(factory.canBuild(filter("w320h300"))).isTrue();
		ASSERT.that(factory.canBuild(filter("w320"))).isTrue();

		// ignore invalid configuration
		ASSERT.that(factory.canBuild(filter("320w300"))).isFalse();
		ASSERT.that(factory.canBuild(filter("w320w300"))).isFalse();
		ASSERT.that(factory.canBuild(filter("h320h200"))).isFalse();
	}

}
