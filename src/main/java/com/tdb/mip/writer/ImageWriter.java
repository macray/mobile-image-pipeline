package com.tdb.mip.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageWriter {

    public abstract void save(BufferedImage image, File file) throws IOException;

    public abstract void save(BufferedImage image, String file) throws IOException;

}