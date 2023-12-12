package com.douzon.blooming.employee.exception;

public class ImageUploadException extends RuntimeException {

  public ImageUploadException() {
    super("지원하지 않는 이미지 타입입니다.");
  }

  public ImageUploadException(String message) {
    super(message);
  }
}
