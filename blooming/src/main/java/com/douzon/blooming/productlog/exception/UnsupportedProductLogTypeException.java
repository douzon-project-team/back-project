package com.douzon.blooming.productlog.exception;

public class UnsupportedProductLogTypeException extends RuntimeException {

  public UnsupportedProductLogTypeException() {
    super("Unsupported product log type");
  }
}
