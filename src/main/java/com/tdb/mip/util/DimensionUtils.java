package com.tdb.mip.util;

/**
 * Created by mcy on 04/07/2016.
 */
public class DimensionUtils {

    public static int computeWeightBasedOn(PixelRounding pixelRounding, int originalW, int originalH, int targetH) {
        float ratio = (float) originalH / (float) targetH;
        return pixelRounding.round(originalW / ratio);
    }

    public static int computeHeightBasedOn(PixelRounding pixelRounding, int originalW, int originalH, int targetW) {
        float ratio = (float) originalW / (float) targetW;
        return pixelRounding.round(originalH / ratio);
    }

}
