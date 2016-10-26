package com.tdb.mip.pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.filter.Resize;

/**
 * Created by vaudauxr on 18/01/15.
 */
public class IOSPipelineFactory extends BasePipelineFactory {

    @Override
    public Pipeline createPipeline(File file, Configuration configuration) {
        String fileName = file.getName();
        // an icon must be generated with a higher density
        List<Density> densities = configuration.getIosTargetDensities();
        Pipeline pipeline = super.createPipeline(file, configuration);

        if (fileName.contains("-icon")) {
            // make the list modifiable
            densities = new ArrayList<>();
            densities.add(new Density("icon-120", "120", 0.1171875f));
            densities.add(new Density("icon-180", "180", 0.17578125f));

            densities.add(new Density("icon-76", "76", 0.07421875f));
            densities.add(new Density("icon-152", "152", 0.1484375f));
            densities.add(new Density("icon-167", "167", 0.1630859375f));
            densities.add(new Density("icon-40", "40", 0.0390625f));
            densities.add(new Density("icon-80", "80", 0.078125f));

            // make the list unmodifiable again
            densities = Collections.unmodifiableList(densities);

            // the ratio has been computed based on 1024x1024 resolution
            pipeline.getFilters().add(new Resize(1024,1024, pipeline.getPixelRounding()));
            //pipeline.getImageReader().setOutputSize(1024,1024);
            pipeline.setOverrideSourceDensity(new Density("1","", 1f));
        }

        pipeline.setTargetDensities(densities);
        return pipeline;
    }
}
