package com.tdb.mip.pipeline;

import static org.truth0.Truth.ASSERT;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.AndroidDensity;

@RunWith(MockitoJUnitRunner.class)
public class AndroidPipelineFactoryTest {

    private AndroidPipelineFactory factory;

    @Mock
    private Configuration globalConfig;

    @Before
    public void setUp() throws IOException {
        factory = new AndroidPipelineFactory();

        Mockito.when(globalConfig.getAndroidDefaultTargetDensities()).thenReturn( //
                Arrays.asList(AndroidDensity.HDPI, AndroidDensity.XHDPI, AndroidDensity.XXHDPI) //
        );

    }

    @Test
    public void check_icon_marker_add_an_extra_target_density() {
        Pipeline pipeline = factory.createPipeline(new File("name-icon.png"), globalConfig);
        ASSERT.that(pipeline.getTargetDensities()).has()
                .allFrom(Arrays.asList(AndroidDensity.HDPI, AndroidDensity.XHDPI, AndroidDensity.XXHDPI, AndroidDensity.XXXHDPI));
    }

    @Test
    public void android_target_densities_are_accessible_via_pipeline() {
        Pipeline pipeline = factory.createPipeline(new File("name.png"), globalConfig);
        ASSERT.that(pipeline.getTargetDensities()).has().allFrom(Arrays.asList(AndroidDensity.HDPI, AndroidDensity.XHDPI, AndroidDensity.XXHDPI));
    }
}