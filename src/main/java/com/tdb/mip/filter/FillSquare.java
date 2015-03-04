package com.tdb.mip.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class FillSquare implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FillSquare.class);

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("fill square");

        int w = image.getWidth();
        int h = image.getHeight();

        if (w == h) {
            // nothing to do
            return deepCopy(image);
        }

        int squareLength = Math.max(image.getWidth(), image.getHeight());
        BufferedImage square = new BufferedImage(squareLength, squareLength, image.getType());

        // copy the original image to the center of the square one
        int xShift = 0;
        int yShift = 0;
        if (w > h) {
            // fill top and bottom
            yShift = (int) ((w - h) / 2f);
        } else {
            // fill right and left
            xShift = (int) ((h - w) / 2f);
        }

        // copy image
        Graphics2D g2 = square.createGraphics();
        g2.drawImage(image, null, xShift, yShift);
        g2.dispose();

        if (w > h) {
            // fill top and bottom
            for (int x = 0; x < square.getWidth(); x++) {
                for (int y = yShift; y >= 0; y--) {
                    square.setRGB(x, y, square.getRGB(x, y + 1));
                }

                for (int y = yShift + image.getHeight(); y < square.getHeight(); y++) {
                    square.setRGB(x, y, square.getRGB(x, y - 1));
                }
            }
        } else {
            // fill right and left
            for (int y = 0; y < square.getHeight(); y++) {
                for (int x = xShift; x >= 0; x--) {
                    square.setRGB(x, y, square.getRGB(x + 1, y));
                }

                for (int x = xShift + image.getWidth(); x < square.getWidth(); x++) {
                    square.setRGB(x, y, square.getRGB(x - 1, y));
                }
            }
        }

        return square;
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    @Override
    public Filter copy() {
        return new FillSquare();
    }
}
