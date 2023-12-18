package com.douzon.blooming.employee.exception;

public class UnsupportedTargetTypeException extends RuntimeException {

  public UnsupportedTargetTypeException() {
    super("지원하지 않는 타입입니다.");
  }
}
