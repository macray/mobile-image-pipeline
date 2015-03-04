package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;

public class AutoCropFactory implements FilterFactory<AutoCrop> {

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        return "autocrop".equals(filterDescription.getRawDescription());
    }

    @Override
    public AutoCrop build(FilterDescription filterDescription, Pipeline pipeline) {
        return new AutoCrop();
    }

}
