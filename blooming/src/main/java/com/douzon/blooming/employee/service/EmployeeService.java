package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;

public interface EmployeeService {
    boolean login(LoginDto dto);
    void insertEmployee(RequestEmployeeDto dto);
    boolean idCheck(String id);
    boolean employeeNoCheck(Long employeeNo);
    ResponseEmployeeDto getEmployeeByNo(Long employeeNo);
    void updateEmployee(RequestEmployeeDto dto);
    void deleteEmployee(Long employeeNo);
}
