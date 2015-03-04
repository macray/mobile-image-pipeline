package com.tdb.mip.exception;

import com.tdb.mip.filter.FilterDescription;

public class FilterDescriptionNotUnderstoodException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FilterDescriptionNotUnderstoodException(FilterDescription filterDescription) {
        super("The filter description '" + filterDescription.getRawDescription() + "' is not understood by any registered Filters");
    }
}
