package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;


public class HFlipFactory implements FilterFactory<HFlip> {

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        return "hflip".equals(filterDescription.getRawDescription());
    }

    @Override
    public HFlip build(FilterDescription filterDescription, Pipeline pipeline) {
        return new HFlip();
    }


}
