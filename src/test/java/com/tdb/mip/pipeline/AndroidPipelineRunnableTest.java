package com.tdb.mip.pipeline;

import static com.google.common.truth.Truth.ASSERT;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.batik.transcoder.TranscoderException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.reader.ImageReader;
import com.tdb.mip.util.PixelRoundingHalfDown;
import com.tdb.mip.writer.ImageWriter;

@RunWith(MockitoJUnitRunner.class)
public class AndroidPipelineRunnableTest {

    private Pipeline pipeline;

    private AndroidPipelineRunnable androidPipelineRunnable;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ImageReader imageReader;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ImageWriter imageWriter;

    @Mock
    private Configuration configuration;

    @Captor
    private ArgumentCaptor<BufferedImage> bufferedImageCaptor;

    @Before
    public void setUp(){
        pipeline = new Pipeline();
        pipeline.setImageReader(imageReader);
        pipeline.setImageWriter(imageWriter);
        pipeline.setSourceFile(new File("fictive-file"));
        pipeline.setTargetDensities(Arrays.asList(AndroidDensity.MDPI, AndroidDensity.HDPI));
        pipeline.setFilters(new ArrayList<Filter>());
        pipeline.setPixelRounding(new PixelRoundingHalfDown());
        
        when(configuration.getAndroidSourceDensity()).thenReturn(AndroidDensity.XXXHDPI);

        androidPipelineRunnable = new AndroidPipelineRunnable(pipeline, configuration);
    }

    @Test
    public void save_one_file_per_target_density() throws IOException {
        androidPipelineRunnable.run();

        int targetDensityCount = pipeline.getTargetDensities().size();
        verify(imageWriter, times(targetDensityCount)).save(any(BufferedImage.class), any(String.class));
    }

    @Test
    public void check_generated_images_have_a_correct_size_if_reader_supports_resizing() throws IOException, TranscoderException {
        // read an image of 100x200 px in XXXHDPI
        imageReader = new DummyImageReaderWhichSupportsResizing(100, 200);
        pipeline.setImageReader(imageReader);

        check_generated_images_have_a_correct_size();
    }

    @Test
    public void check_generated_images_have_a_correct_size_if_reader_does_not_support_resizing() throws IOException, TranscoderException {
        when(imageReader.supportResizing()).thenReturn(false);
        // read an image of 100x200 px in XXXHDPI
        when(imageReader.read(any(File.class))).thenReturn(new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB));

        check_generated_images_have_a_correct_size();
    }

    private void check_generated_images_have_a_correct_size() throws IOException, TranscoderException {
        androidPipelineRunnable.run();

        verify(imageWriter, times(2)).save(bufferedImageCaptor.capture(), anyString());

        // width = 25 height = 50 (MDPI) 1x
        // width = 37.5 height = 75  (HDPI) 1.5x
        // width = 100 height = 200 (XXXHDPI) 4x
        BufferedImage mdpiImage = bufferedImageCaptor.getAllValues().get(0);
        BufferedImage hdpiImage = bufferedImageCaptor.getAllValues().get(1);

        if(mdpiImage.getWidth() > hdpiImage.getWidth()){
            // swap image
            BufferedImage temp = mdpiImage;
            mdpiImage = hdpiImage;
            hdpiImage = temp;
        }

        ASSERT.that(mdpiImage.getWidth()).isEqualTo(25);
        ASSERT.that(mdpiImage.getHeight()).isEqualTo(50);

        ASSERT.that(hdpiImage.getWidth()).isEqualTo(37); // 37.5
        ASSERT.that(hdpiImage.getHeight()).isEqualTo(75);
    }


    @Test
    public void filters_have_been_applied() throws InterruptedException {
        AtomicInteger applyCounter = new AtomicInteger();

        List<Filter> filters = new LinkedList<>();
        filters.add(new DummyFilter(applyCounter));
        filters.add(new DummyFilter(applyCounter));
        filters.add(new DummyFilter(applyCounter));
        pipeline.setFilters(filters);

        androidPipelineRunnable.run();

        int numberOfExpectedApply = filters.size() * pipeline.getTargetDensities().size();
        ASSERT.that(applyCounter.get()).isEqualTo(numberOfExpectedApply);
    }

    private static class DummyFilter implements Filter {

        private AtomicInteger applyCounter;

        public DummyFilter(AtomicInteger applyCounter){
            this.applyCounter = applyCounter;
        }

        @Override
        public BufferedImage applyTo(BufferedImage image) {
            applyCounter.incrementAndGet();
            return image;
        }

        @Override
        public Filter copy() {
            return new DummyFilter(applyCounter);
        }
    }

    private static class DummyImageReaderWhichSupportsResizing implements ImageReader {

        private int w = -1;
        private int h = -1;

        public DummyImageReaderWhichSupportsResizing(int w, int h){
            this.w = w;
            this.h = h;
        }

        @Override
        public BufferedImage read(String file) throws TranscoderException, IOException {
            return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }

        @Override
        public BufferedImage read(File file) throws TranscoderException, IOException {
            return new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }

        @Override
        public boolean supportResizing() {
            return true;
        }

        @Override
        public void setOutputSize(int w, int h) {
            this.w = w;
            this.h = h;
        }
    }
}