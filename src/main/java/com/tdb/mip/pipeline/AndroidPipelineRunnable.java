package com.tdb.mip.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;

public class AndroidPipelineRunnable extends BasePipelineRunnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidPipelineRunnable.class);

    public AndroidPipelineRunnable(Pipeline pipeline, Configuration configuration) {
        super(pipeline, configuration);
    }

	@Override
	protected Density getSourceDensity() {
		return configuration.getAndroidSourceDensity();
	}

	@Override
	protected String computeOutputFilename(Density targetDensity) {
		return configuration.getAndroidTargetDir() + "/drawable-" + targetDensity.getSuffix() + "/" + pipeline.getOutFileName();
	}

}
