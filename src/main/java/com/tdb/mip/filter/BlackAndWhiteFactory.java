package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;


public class BlackAndWhiteFactory implements FilterFactory<BlackAndWhite> {

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        return "bw".equals(filterDescription.getRawDescription());
    }

    @Override
    public BlackAndWhite build(FilterDescription filterDescription, Pipeline pipeline) {
        return new BlackAndWhite();
    }


}
