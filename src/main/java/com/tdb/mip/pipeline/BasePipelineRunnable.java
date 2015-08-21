package com.tdb.mip.pipeline;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.exception.TooManyResizeFilterException;
import com.tdb.mip.filter.Filter;
import com.tdb.mip.filter.Resize;
import com.tdb.mip.reader.ImageReader;
import org.apache.batik.transcoder.TranscoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public abstract class BasePipelineRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasePipelineRunnable.class);

    protected final Pipeline pipeline;
    protected final Configuration configuration;

    protected float resizeFilterRatioW;
    protected float resizeFilterRatioH;

    
    public BasePipelineRunnable(Pipeline pipeline, Configuration configuration) {
        this.pipeline = pipeline;
        this.configuration = configuration;
    }

    @Override
    final public void run() {
        try {
            generateTargetDensities(pipeline.getImageReader(), configuration);
        } catch (Exception e) {
            LOGGER.error("Failed to process " + pipeline.getSourceFile(), e);
        }
    }
    
    private void computeResizeFilterRatio(Resize resize, BufferedImage originalImage) {
        if (resize != null) {
            // a resize is needed (not related to the density)
            resize.updateTargetWidthOrHeightIfNeeded(originalImage.getWidth(), originalImage.getHeight());
            resizeFilterRatioW = resize.getW() / (float) originalImage.getWidth();
            resizeFilterRatioH = resize.getH() / (float) originalImage.getHeight();
        } else {
            resizeFilterRatioW = 1f;
            resizeFilterRatioH = 1f;
        }
	}
    
    private Resize removeResizeFilterFromPipeline() {
    	// HACK: UGLY !! need to properly handle multi resize, and ability to disable resizing reordering
    	if(pipeline.isDisallowResizeReordering()) {
    		return null;
    	}
    	
        Iterator<Filter> iterator = pipeline.getFilters().iterator();
        Resize resize = null;
        while (iterator.hasNext()) {
            Filter filter = iterator.next();
            if (filter instanceof Resize) {
                boolean alreadyFound = resize != null;
                if (alreadyFound) {
                    throw new TooManyResizeFilterException();
                }
                resize = (Resize) filter;
                iterator.remove();
            }
        }

        return resize;
    }
    
    private void generateTargetDensities(ImageReader imageReader, Configuration configuration) throws IOException, TranscoderException {
        // load original image
    	File source = pipeline.getSourceFile();
        BufferedImage originalImage = imageReader.read(source);
        
        // compute filter ratio
        Resize resize = removeResizeFilterFromPipeline();
        computeResizeFilterRatio(resize, originalImage);
        
        Density sourceDensity = getSourceDensity();
        for(Density targetDensity : pipeline.getTargetDensities()) {
        	try {
        		BufferedImage transformedImage = computeTransformedImage(imageReader, source, originalImage, sourceDensity, targetDensity);
        		String output = computeOutputFilename(targetDensity);
                pipeline.getImageWriter().save(transformedImage, output);
            } catch (Exception e) {
                LOGGER.error("Error when processing '" + source.getAbsolutePath() + "'", e);
            }
        }

    }
    
    private BufferedImage computeTransformedImage(ImageReader imageReader, File source, BufferedImage originalImage, Density sourceDensity,
			Density targetDensity) throws TranscoderException, IOException {
		// compute target size ( = resizeRatio * densityRatio)
		float densityRatio = DensityUtils.getRatio(sourceDensity, targetDensity);

        int targetW, targetH;

		BufferedImage transformedImage;
		if (imageReader.supportResizing() && !pipeline.isDisallowResizeReordering()) {
            targetW = pipeline.getPixelRounding().round(originalImage.getWidth() * densityRatio * resizeFilterRatioW);
            targetH = pipeline.getPixelRounding().round(originalImage.getHeight() * densityRatio * resizeFilterRatioH);

            // load the image to its final size
		    imageReader.setOutputSize(targetW, targetH);
		    transformedImage = imageReader.read(source);

            // apply filters
            transformedImage = applyFilters(transformedImage);
		} else {
            transformedImage = applyFilters(originalImage);

            // recompute the target size here for supporting filter which can resize orginal image size (like autocrop)
            targetW = pipeline.getPixelRounding().round(transformedImage.getWidth() * densityRatio * resizeFilterRatioW);
            targetH = pipeline.getPixelRounding().round(transformedImage.getHeight() * densityRatio * resizeFilterRatioH);

            // resize to its final size
		    if(!pipeline.isDisallowResizeReordering()) {
		    	Resize resizeToTargetDensity = new Resize(targetW, targetH, pipeline.getPixelRounding());
		    	transformedImage = resizeToTargetDensity.applyTo(transformedImage);
		    }
		}
		return transformedImage;
	}

	

    private BufferedImage applyFilters(BufferedImage image) {
        for (Filter filter : pipeline.getFilters()) {
            image = filter.applyTo(image);
        }
        return image;
    }
    
    protected abstract Density getSourceDensity();

    protected abstract String computeOutputFilename(Density targetDensity);
    	   
}
