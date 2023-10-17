package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.EmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginDto;

public interface EmployeeService {
    void login(LoginDto dto);
    void insertEmployee(EmployeeDto dto);
    void updateEmployee(EmployeeDto dto);
}
