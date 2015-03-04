package com.tdb.mip.pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tdb.mip.Configuration;
import com.tdb.mip.density.Density;
import com.tdb.mip.density.DensityUtils;
import com.tdb.mip.density.AndroidDensity;

/**
 * Created by vaudauxr on 18/01/15.
 */
public class AndroidPipelineFactory extends BasePipelineFactory {


    @Override
    public Pipeline createPipeline(File file, Configuration configuration) {
        String fileName = file.getName();
        // an icon must be generated with a higher density
        List<Density> densities = configuration.getAndroidDefaultTargetDensities();
        if (fileName.contains("-icon")) {
            // make the list modifiable
            densities = new ArrayList<>(densities);
            Density higherDensity = DensityUtils.findHighestDensity(densities);
            Density higherDensityPlusOne = DensityUtils.closestUpperDensity(AndroidDensity.ALL, higherDensity);
            densities.add(higherDensityPlusOne);
            // make the list unmodifiable again
            densities = Collections.unmodifiableList(densities);

            // remove "-icon" from the name
            // because we have already process it
            fileName = fileName.replaceFirst("-icon", "");
            file = new File(fileName);
        }

        Pipeline pipeline = super.createPipeline(file, configuration);

        pipeline.setTargetDensities(densities);

        return pipeline;
    }
}
