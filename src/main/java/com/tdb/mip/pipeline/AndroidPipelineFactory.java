package com.tdb.mip.pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.density.AndroidDensity;
import com.tdb.mip.filter.Resize;

/**
 * Created by vaudauxr on 18/01/15.
 */
public class AndroidPipelineFactory extends BasePipelineFactory {


    @Override
    public Pipeline createPipeline(File file, Configuration configuration) {
        String fileName = file.getName();
        // an icon must be generated with a higher density
        List<Density> densities = configuration.getAndroidDefaultTargetDensities();
        Pipeline pipeline = super.createPipeline(file, configuration);

        if (fileName.contains("-icon")) {
            // make the list modifiable
            densities = new ArrayList<>(densities);
            Density higherDensity = DensityUtils.findHighestDensity(densities);
            Density higherDensityPlusOne = DensityUtils.closestUpperDensity(AndroidDensity.ALL, higherDensity);
            densities.add(higherDensityPlusOne);
            // make the list unmodifiable again
            densities = Collections.unmodifiableList(densities);

            pipeline.getFilters().add(new Resize(192,192, pipeline.getPixelRounding()));
        }


        pipeline.setTargetDensities(densities);

        return pipeline;
    }
}
