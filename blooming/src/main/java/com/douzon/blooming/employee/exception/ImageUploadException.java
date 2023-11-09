package com.douzon.blooming.employee.exception;

public class ImageUploadException extends RuntimeException {

  public ImageUploadException() {
    super("Image format error : use jpg image");
  }

  public ImageUploadException(String message) {
    super(message);
  }
}
