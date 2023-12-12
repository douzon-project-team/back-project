package com.douzon.blooming.auth.exception;

public class UnsupportedEmployeeRoleException extends RuntimeException {

  public UnsupportedEmployeeRoleException() {
    super("지원하지 않는 권한(Type) 입니다.");
  }
}
