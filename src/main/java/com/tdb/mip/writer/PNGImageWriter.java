package com.tdb.mip.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PNGImageWriter implements ImageWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PNGImageWriter.class);

    /*
     * (non-Javadoc)
     *
     * @see com.tdb.mip.writer.Saver#writer(java.awt.image.BufferedImage,
     * java.io.File)
     */
    @Override
    public void save(BufferedImage image, File file) throws IOException {
        // create parent folder if needed
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }

        ImageIO.write(image, "png", file);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.tdb.mip.writer.Saver#writer(java.awt.image.BufferedImage,
     * java.lang.String)
     */
    @Override
    public void save(BufferedImage image, String file) throws IOException {
        save(image, new File(file));
    }

}
