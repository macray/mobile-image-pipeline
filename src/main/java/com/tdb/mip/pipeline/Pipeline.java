package com.tdb.mip.pipeline;

import java.io.File;
import java.util.List;

import com.tdb.mip.density.Density;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.reader.ImageReader;
import com.tdb.mip.util.PixelRounding;
import com.tdb.mip.writer.ImageWriter;

public class Pipeline {
	private PixelRounding pixelRounding;
    private ImageReader imageReader;
    private List<Filter> filters;
    private ImageWriter imageWriter;
    private File sourceFile;
    private String outFileName;
    // TODO: Android specific values should be removed
    // FIXME: create Density interface shared by iOS and Android
    private List<Density> targetDensities;

    public Pipeline() {
    }

    public List<Density> getTargetDensities() {
        return targetDensities;
    }

    public void setTargetDensities(List<Density> targetDensities) {
        this.targetDensities = targetDensities;
    }
    
    public PixelRounding getPixelRounding() {
		return pixelRounding;
	}

	public void setPixelRounding(PixelRounding pixelRounding) {
		this.pixelRounding = pixelRounding;
	}

	public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    public ImageReader getImageReader() {
        return imageReader;
    }

    public void setImageReader(ImageReader imageReader) {
        this.imageReader = imageReader;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public void setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
    }

}
