package com.tdb.mip.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;

public class WindowsPhonePipelineRunnable extends BasePipelineRunnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindowsPhonePipelineRunnable.class);

    public WindowsPhonePipelineRunnable(Pipeline pipeline, Configuration configuration) {
        super(pipeline, configuration);
    }

	@Override
	protected Density getSourceDensity() {
		return new Density("1x", "", 1f);
	}

	@Override
	protected String computeOutputFilename(Density targetDensity) {
		return configuration.getWpTargetDir() + "/" + pipeline.getOutFileName();
	}

}
