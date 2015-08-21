package com.tdb.mip.pipeline;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.WPDensity;
import com.tdb.mip.filter.FillTo;
import com.tdb.mip.filter.Resize;
import com.tdb.mip.util.PixelRoundingHalfDown;

/**
 * Created by vaudauxr on 18/01/15.
 */
public class WindowsPhonePipelineFactory extends BasePipelineFactory {

	@Override
	public Pipeline createPipeline(File file, Configuration configuration) {
		String fileName = file.getName();

		Pipeline pipeline = super.createPipeline(file, configuration);

		// TODO: merge marker & filter
		if (fileName.contains("-appbar")) {
			// make the list modifiable
			pipeline.getFilters().add(new Resize(26, 26, new PixelRoundingHalfDown()));
			pipeline.getFilters().add(new FillTo(48, 48, new Color(0f, 0f, 0f, 0f)));
			//pipeline.getFilters().add(new FillTo(48, 48, new Color(1f, 0f, 0f, 1f)));
			pipeline.setDisallowResizeReordering(true);
		}
		
		if(fileName.contains("-noreorder")) {
			pipeline.setDisallowResizeReordering(true);
		}

		// currently only 1 density is supported by WP
		pipeline.setTargetDensities(Arrays.asList(WPDensity._1));

		return pipeline;
	}
}
