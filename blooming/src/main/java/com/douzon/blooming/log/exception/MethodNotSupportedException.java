package com.douzon.blooming.log.exception;

public class MethodNotSupportedException extends RuntimeException {
    public MethodNotSupportedException(String method) {
        super(method + " is not supported");
    }
}
