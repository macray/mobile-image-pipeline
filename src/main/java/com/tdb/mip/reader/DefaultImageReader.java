package com.tdb.mip.reader;

import com.tdb.mip.exception.MethodNotSupportedException;
import org.apache.batik.transcoder.TranscoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DefaultImageReader implements ImageReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultImageReader.class);

    @Override
    public BufferedImage read(String file) throws TranscoderException, IOException {
        return read(new File(file));
    }

    @Override
    public BufferedImage read(File file) throws TranscoderException, IOException {
        LOGGER.info("reading " + file);
        return ImageIO.read(file);
    }

    @Override
    public boolean supportResizing() {
        return false;
    }

    @Override
    public void setOutputSize(int w, int h) {
        throw new MethodNotSupportedException();
    }

}
