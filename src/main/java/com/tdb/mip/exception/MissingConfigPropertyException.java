package com.tdb.mip.exception;

public class MissingConfigPropertyException extends RuntimeException {
    private static final long serialVersionUID = 5469172151484844242L;

    public MissingConfigPropertyException(String message) {
        super(message);
    }

}
