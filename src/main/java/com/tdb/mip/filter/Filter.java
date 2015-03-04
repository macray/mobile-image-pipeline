package com.tdb.mip.filter;

import java.awt.image.BufferedImage;

public interface Filter {

    /**
     * DEV NOTE: The filter must not alter the original image
     *
     * @param image original image
     * @return return the filtered new image
     */
    public abstract BufferedImage applyTo(BufferedImage image);

    public Filter copy();
}