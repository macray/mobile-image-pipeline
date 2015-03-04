package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;

public class FillSquareFactory implements FilterFactory<FillSquare> {

    @Override
    public boolean canBuild(FilterDescription filterDescription) {
        return "fillsquare".equals(filterDescription.getRawDescription());
    }

    @Override
    public FillSquare build(FilterDescription filterDescription, Pipeline pipeline) {
        return new FillSquare();
    }

}
