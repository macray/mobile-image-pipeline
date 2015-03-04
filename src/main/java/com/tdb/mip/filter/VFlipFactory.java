package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;


public class VFlipFactory implements FilterFactory<VFlip> {

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        return "vflip".equals(filterDescription.getRawDescription());
    }

    @Override
    public VFlip build(FilterDescription filterDescription, Pipeline pipeline) {
        return new VFlip();
    }

}
