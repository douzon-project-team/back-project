package com.douzon.blooming.employee.exception;

public class NotFoundImageException extends
    RuntimeException {
  public NotFoundImageException() {
    super("Not Found Image");
  }
}
