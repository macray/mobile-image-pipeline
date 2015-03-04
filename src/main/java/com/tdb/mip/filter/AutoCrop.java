package com.tdb.mip.filter;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class AutoCrop implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCrop.class);

    /*
     * (non-Javadoc)
     *
     * @see com.tdb.mip.filter.Filter#applyTo(java.awt.image.BufferedImage,
     * java.lang.Object)
     */
    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("autocropping");

        int w = image.getWidth();
        int h = image.getHeight();

        // left to right
        int backgroundColor = image.getRGB(0, 0);

        int cropX = 0;
        loop1:
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (image.getRGB(x, y) != backgroundColor) {
                    cropX = x;
                    break loop1;
                }
            }
        }

        // right to left
        if (cropX == 0) {
            backgroundColor = image.getRGB(w - 1, 0);
        }
        int cropWidth = w;
        loop2:
        for (int x = w - 1; x >= 0; x--) {
            for (int y = 0; y < h; y++) {
                if (image.getRGB(x, y) != backgroundColor) {
                    cropWidth = x - cropX;
                    break loop2;
                }
            }
        }

        // top to bottom
        if (cropWidth == w) {
            backgroundColor = image.getRGB(0, 0);
        }
        int cropY = 0;
        loop3:
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (image.getRGB(x, y) != backgroundColor) {
                    cropY = y;
                    break loop3;
                }
            }
        }

        // bottom to top
        if (cropY == 0) {
            backgroundColor = image.getRGB(0, h - 1);
        }
        int cropHeight = h;
        loop3:
        for (int y = h - 1; y >= 0; y--) {
            for (int x = 0; x < w; x++) {
                if (image.getRGB(x, y) != backgroundColor) {
                    cropHeight = y - cropY;
                    break loop3;
                }
            }
        }

        BufferedImage bufferedImage = Scalr.crop(image, cropX, cropY, cropWidth, cropHeight);

        return bufferedImage;
    }

    @Override
    public Filter copy() {
        return new AutoCrop();
    }

}
