package com.douzon.blooming.log.exception;

public class UnsupportedLogTypeException extends RuntimeException {

  public UnsupportedLogTypeException() {
    super("Unsupported product log type");
  }
}
