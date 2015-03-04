package com.tdb.mip.exception;

import com.tdb.mip.filter.FilterDescription;

public class AmbigiousFilterDescriptionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AmbigiousFilterDescriptionException(FilterDescription filterDescription) {
        super("At least 2 different filters can understand the following filter description " + filterDescription.getRawDescription());
    }
}
