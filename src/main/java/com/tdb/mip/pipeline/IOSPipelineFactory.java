package com.tdb.mip.pipeline;

import java.io.File;

import com.tdb.mip.Configuration;

/**
 * Created by vaudauxr on 18/01/15.
 */
public class IOSPipelineFactory extends BasePipelineFactory {

    @Override
    public Pipeline createPipeline(File file, Configuration configuration) {
        Pipeline pipeline = super.createPipeline(file, configuration);
        pipeline.setTargetDensities(configuration.getIosTargetDensities());
        return pipeline;
    }
}
