package com.douzon.blooming.employee.exception;


public class NotFoundEmployeeException extends RuntimeException {

  public NotFoundEmployeeException() {
    super("해당 사원을 찾을 수 없습니다.");
  }

  public NotFoundEmployeeException(String message) {
    super(message);
  }
}
