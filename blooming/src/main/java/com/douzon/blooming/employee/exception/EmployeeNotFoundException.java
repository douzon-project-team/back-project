package com.douzon.blooming.employee.exception;

import java.util.function.Supplier;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException() {
        super("Employee is not found");
    }
}
