package com.douzon.blooming.log.exception;

public class UnsupportedLogTypeException extends RuntimeException {

  public UnsupportedLogTypeException() {
    super("지원하지 않는 로그 타입입니다.");
  }
}
