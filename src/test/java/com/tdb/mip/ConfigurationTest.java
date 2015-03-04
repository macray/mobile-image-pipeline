package com.tdb.mip;

import com.tdb.mip.exception.MissingConfigPropertyException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.ASSERT;

public class ConfigurationTest {

    public static final String BASE_PATH = "src/test/resources/config/";
    private Configuration config;

    @Before
    public void setUp(){
        config = new Configuration();
    }

    @Test
    public void when_all_android_properties_specific_to_android_are_not_present_consider_android_profile_has_disabled() throws IOException {
        config.load(BASE_PATH + "ios-only.properties");
        ASSERT.that(config.hasAndroidConfiguration()).isFalse();
        ASSERT.that(config.hasIosConfiguration()).isTrue();
    }

    @Test
    public void when_all_ios_properties_specific_to_android_are_not_present_consider_ios_profile_has_disabled() throws IOException {
        config.load(BASE_PATH + "android-only.properties");
        ASSERT.that(config.hasIosConfiguration()).isFalse();
        ASSERT.that(config.hasAndroidConfiguration()).isTrue();
    }

    @Test(expected = MissingConfigPropertyException.class)
    public void when_missing_properties_throw_an_exception() throws IOException {
        config.load(BASE_PATH + "android-missing-source.properties");
    }

    @Test(expected = MissingConfigPropertyException.class)
    public void an_empty_config_throw_an_exception() throws IOException {
        config.load(BASE_PATH + "empty.properties");
    }

    @Test
    public void when_android_and_ios_properties_are_defined_both_are_valid() throws IOException {
        config.load(BASE_PATH + "all.properties");
        ASSERT.that(config.hasAndroidConfiguration()).isTrue();
        ASSERT.that(config.hasIosConfiguration()).isTrue();
    }
}
