package com.tdb.mip.filter;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

public class VFlip implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(VFlip.class);

    @Override
    public BufferedImage applyTo(BufferedImage image) {
        LOGGER.debug("vertical flip");
        return Scalr.rotate(image, Rotation.FLIP_VERT);
    }

    @Override
    public Filter copy() {
        return new VFlip();
    }
}
