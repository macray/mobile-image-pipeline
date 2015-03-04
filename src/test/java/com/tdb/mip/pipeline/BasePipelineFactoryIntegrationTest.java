package com.tdb.mip.pipeline;

import static org.mockito.Mockito.*;
import static org.truth0.Truth.ASSERT;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.tdb.mip.Configuration;
import com.tdb.mip.filter.AutoCrop;
import com.tdb.mip.filter.AutoCropFactory;
import com.tdb.mip.filter.FillSquareFactory;
import com.tdb.mip.filter.Resize;
import com.tdb.mip.filter.ResizeFactory;
import com.tdb.mip.reader.DefaultImageReader;
import com.tdb.mip.reader.SVGImageReader;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

// FIXME: rename me
@RunWith(MockitoJUnitRunner.class)
public class BasePipelineFactoryIntegrationTest {

    private BasePipelineFactory factory;

    @Mock
    private Configuration globalConfig;

    @Before
    public void setUp() throws IOException {
        factory = new BasePipelineFactory();
        factory.registerFilterFactory(new ResizeFactory());
        factory.registerFilterFactory(new AutoCropFactory());
        factory.registerFilterFactory(new FillSquareFactory());

        when(globalConfig.hasAndroidConfiguration()).thenReturn(true);
        when(globalConfig.hasIosConfiguration()).thenReturn(true);
    }

    @Test
    public void build_simplest_svg_file_pipeline() {
        Pipeline pipeline = factory.createPipeline(new File("robocop2.svg"), globalConfig);
        ASSERT.that(pipeline.getFilters()).isEmpty();
        ASSERT.that(pipeline.getImageWriter()).isNotNull();
        ASSERT.that(pipeline.getImageReader()).isNotNull();
        ASSERT.that(pipeline.getImageReader()).isA(SVGImageReader.class);
        ASSERT.that(pipeline.getOutFileName()).isEqualTo("robocop2.png");
    }

    @Test
    public void resize_image_to_h140() {
        Pipeline pipeline = factory.createPipeline(new File("name-h140.png"), globalConfig);
        ASSERT.that(pipeline.getFilters().size()).isEqualTo(1);
        ASSERT.that(pipeline.getFilters().get(0)).isA(Resize.class);

        Resize filter = (Resize) pipeline.getFilters().get(0);
        ASSERT.that(filter.getH()).isEqualTo(140);
    }

    @Test
    public void build_empty_pipeline() {
        Pipeline pipeline = factory.createPipeline(new File("weird_name32xh120.png"), globalConfig);
        ASSERT.that(pipeline.getFilters()).isEmpty();
        ASSERT.that(pipeline.getImageWriter()).isNotNull();
        ASSERT.that(pipeline.getOutFileName()).isEqualTo("weird_name32xh120.png");
        ASSERT.that(pipeline.getImageReader()).isA(DefaultImageReader.class);
    }

    @Test
    public void build_svg_autocrop_resize_w400_h200() {
        Pipeline pipeline = factory.createPipeline(new File("name-autocrop-w400h200.svg"), globalConfig);
        ASSERT.that(pipeline.getFilters().size()).isEqualTo(2);
        ASSERT.that(pipeline.getFilters().get(0)).isA(AutoCrop.class);
        ASSERT.that(pipeline.getFilters().get(1)).isA(Resize.class);
        ASSERT.that(pipeline.getOutFileName()).isEqualTo("name.png");

        Resize filter = (Resize) pipeline.getFilters().get(1);
        ASSERT.that(filter.getW()).isEqualTo(400);
        ASSERT.that(filter.getH()).isEqualTo(200);

        ASSERT.that(pipeline.getImageWriter()).isNotNull();
        ASSERT.that(pipeline.getImageReader()).isA(SVGImageReader.class);
    }

    @Test
    public void build_svg_autocrop_resize_h400_w200() {
        Pipeline pipeline = factory.createPipeline(new File("name-autocrop-h400w200.svg"), globalConfig);
        ASSERT.that(pipeline.getFilters().size()).isEqualTo(2);
        ASSERT.that(pipeline.getFilters().get(0)).isA(AutoCrop.class);
        ASSERT.that(pipeline.getFilters().get(1)).isA(Resize.class);
        ASSERT.that(pipeline.getOutFileName()).isEqualTo("name.png");

        Resize filter = (Resize) pipeline.getFilters().get(1);
        ASSERT.that(filter.getW()).isEqualTo(200);
        ASSERT.that(filter.getH()).isEqualTo(400);

        ASSERT.that(pipeline.getImageWriter()).isNotNull();
        ASSERT.that(pipeline.getImageReader()).isA(SVGImageReader.class);
    }

}
