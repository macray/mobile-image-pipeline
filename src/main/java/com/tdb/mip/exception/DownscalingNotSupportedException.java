package com.tdb.mip.exception;

public class DownscalingNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public DownscalingNotSupportedException(String msg) {
    	super(msg);
    }
}
