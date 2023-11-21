package com.douzon.blooming.employee.exception;


public class NotFoundEmployeeException extends RuntimeException {

  public NotFoundEmployeeException() {
    super("Employee is not found");
  }
}
