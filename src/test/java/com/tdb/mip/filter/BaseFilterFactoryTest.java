package com.tdb.mip.filter;


import java.util.List;

import static com.google.common.truth.Truth.ASSERT;

/**
 * Created by vaudauxr on 19/01/15.
 */
public class BaseFilterFactoryTest {

    public FilterDescription filter(String filterDescription){
        List<FilterDescription> filterDescriptions = FilterDescription.Builder.fromStringDescription(filterDescription);
        ASSERT.that(filterDescriptions.size()).named("must have only 1 FilterDescription").isEqualTo(1);
        return filterDescriptions.get(0);
    }
}
