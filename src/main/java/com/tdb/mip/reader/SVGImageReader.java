package com.tdb.mip.reader;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class SVGImageReader implements ImageReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SVGImageReader.class);

    private int h;
    private int w;

    public SVGImageReader() {
        this.w = -1;
        this.h = -1;
    }

    @Override
    public BufferedImage read(String file) throws TranscoderException, IOException {
        return read(new File(file));
    }

    @Override
    public BufferedImage read(File file) throws TranscoderException, IOException {
        LOGGER.info("reading " + file + "(w=" + w + " h=" + h + ")");
        BufferedImageTranscoder t = new BufferedImageTranscoder();

        // t.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, new Color(0,
        // 0, 0, 0));
        // t.addTranscodingHint(PNGTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER,
        // 100);

        if (w != -1) {
            t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(w));
        }

        if (h != -1) {
            t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(h));
        }

        // Create the transcoder input.
        String svgURI = file.toURI().toURL().toString();
        TranscoderInput input = new TranscoderInput(svgURI);

        // Create the transcoder output.
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(ostream);

        try {
            t.transcode(input, output);
            ostream.flush();
        } finally {
            ostream.close();
        }

        return t.getBufferedImage();
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

    // http://bbgen.net/blog/2011/06/java-svg-to-bufferedimage/
    private class BufferedImageTranscoder extends ImageTranscoder {
        private BufferedImage img = null;

        @Override
        public BufferedImage createImage(int w, int h) {
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            return bi;
        }

        @Override
        public void writeImage(BufferedImage img, TranscoderOutput output) {
            this.img = img;
        }

        public BufferedImage getBufferedImage() {
            return img;
        }
    }
}
