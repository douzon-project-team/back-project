package com.douzon.blooming.auth.exception;

public class UnsupportedEmployeeRoleException extends RuntimeException {

  public UnsupportedEmployeeRoleException() {
    super("Unsupported employeeRole");
  }
}
