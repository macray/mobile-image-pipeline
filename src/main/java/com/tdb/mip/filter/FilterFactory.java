package com.tdb.mip.filter;

import com.tdb.mip.pipeline.Pipeline;

public interface FilterFactory<T extends Filter> {

    public boolean canBuild(FilterDescription filterDescription);

    public T build(FilterDescription filterDescription, Pipeline pipeline);

}
