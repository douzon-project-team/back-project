package com.douzon.blooming.employee.exception;


public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException() {
    super("Employee is not found");
  }
}
