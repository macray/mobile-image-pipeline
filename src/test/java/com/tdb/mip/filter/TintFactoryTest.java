package com.tdb.mip.filter;

import static org.truth0.Truth.ASSERT;

import org.junit.Before;
import org.junit.Test;


public class TintFactoryTest extends BaseFilterFactoryTest{

	private TintFactory factory;

	@Before
	public void setUp() {
		factory = new TintFactory();
	}

	@Test
	public void can_build() {
		ASSERT.that(factory.canBuild(filter("tintFF3600"))).isTrue();
		ASSERT.that(factory.canBuild(filter("tint#FF00AB"))).isTrue();
		ASSERT.that(factory.canBuild(filter("tint#123456"))).isTrue();
		ASSERT.that(factory.canBuild(filter("tint123456"))).isTrue();

		ASSERT.that(factory.canBuild(filter("tint123456,0.12"))).isTrue();
		ASSERT.that(factory.canBuild(filter("tint123456,0,12"))).isTrue();

		ASSERT.that(factory.canBuild(filter("tintJAZZYY"))).isFalse();
		ASSERT.that(factory.canBuild(filter("tint#JAZZYY"))).isFalse();
		ASSERT.that(factory.canBuild(filter("tint#1234567"))).isFalse();
		ASSERT.that(factory.canBuild(filter("tint1234567"))).isFalse();
	}

}
