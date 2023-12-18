package com.douzon.blooming.employee.exception;

public class PasswordDoesNotMatchException extends
    RuntimeException {

  public PasswordDoesNotMatchException() {
    super("password does not match oldPassword : password");
  }
}
