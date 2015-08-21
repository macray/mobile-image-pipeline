package com.tdb.mip.filter;

import static org.truth0.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;


public class FillToFactoryTest extends BaseFilterFactoryTest{

	private FillToFactory factory;

	@Before
	public void setUp() {
		factory = new FillToFactory();
	}

	@Test
	public void filter_detects_his_config_properly() {		
		// detect valid configuration
		ASSERT.that(factory.canBuild(filter("fillw101h102#11223344"))).isTrue();
		ASSERT.that(factory.canBuild(filter("fillh101w102#11223344"))).isTrue();
		ASSERT.that(factory.canBuild(filter("fillw101h102#112233"))).isTrue();
		ASSERT.that(factory.canBuild(filter("fillh101w102#112233"))).isTrue();

		// ignore invalid configuration
		ASSERT.that(factory.canBuild(filter("fillw100h100#11223"))).isFalse();
		ASSERT.that(factory.canBuild(filter("fillw100#112233"))).isFalse();
		ASSERT.that(factory.canBuild(filter("fillh100#112233"))).isFalse();
	}

}
