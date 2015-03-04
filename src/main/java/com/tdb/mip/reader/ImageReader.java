package com.tdb.mip.reader;

import org.apache.batik.transcoder.TranscoderException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageReader {

    public BufferedImage read(String file) throws TranscoderException, IOException;

    public BufferedImage read(File file) throws TranscoderException, IOException;

    public boolean supportResizing();

    public void setOutputSize(int w, int h);
}