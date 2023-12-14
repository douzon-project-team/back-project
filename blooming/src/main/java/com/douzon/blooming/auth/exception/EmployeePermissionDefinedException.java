package com.douzon.blooming.auth.exception;

public class EmployeePermissionDefinedException extends RuntimeException {

  public EmployeePermissionDefinedException() {
    super("접근 권한이 없습니다.");
  }
}
