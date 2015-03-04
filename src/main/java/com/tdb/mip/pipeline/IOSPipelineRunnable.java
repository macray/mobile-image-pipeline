package com.tdb.mip.pipeline;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.IOSDensity;

public class IOSPipelineRunnable extends BasePipelineRunnable {

    public IOSPipelineRunnable(Pipeline pipeline,  Configuration configuration) {
        super(pipeline, configuration);
    }

//    @Override
//    public void generateTargetDensities(ImageReader imageReader, Configuration configuration) throws IOException, TranscoderException {
//        File source = pipeline.getSourceFile();
//        BufferedImage originalImage = imageReader.read(source);
//        
//        updateResizeFilterRatio(originalImage);
//        
//        Density sourceDensity = IOSDensity.X3;
//        for(Density targetDensity : pipeline.getTargetDensities()) {
//        	try {
//        		BufferedImage transformedImage = computeTransformedImage(imageReader, source, originalImage, sourceDensity, targetDensity);
//        		String output = computeOutputFilename(targetDensity);
//                pipeline.getImageWriter().save(transformedImage, output);
//            } catch (Exception e) {
//                LOGGER.error("Error when processing '" + source.getAbsolutePath() + "'", e);
//            }
//        }
//
//    }
    
    @Override
    public String computeOutputFilename(Density targetDensity) {
    	 String defaultOutputFileName = configuration.getIosTargetDir() + "/" + pipeline.getOutFileName();
         String extension = "." + FilenameUtils.getExtension(defaultOutputFileName);
         String defaultOutputFileNameWithoutExt = defaultOutputFileName.replace(extension, "");
         String output = defaultOutputFileNameWithoutExt+ targetDensity.getSuffix() + extension;
     	
         return output;
    }

	@Override
	protected Density getSourceDensity() {
		return IOSDensity.X3;
	}

}
