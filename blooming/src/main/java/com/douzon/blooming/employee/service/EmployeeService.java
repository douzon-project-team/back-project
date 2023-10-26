package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;

public interface EmployeeService {
    boolean login(LoginDto dto);

    void addEmployee(RequestEmployeeDto dto);

    String idCheck(String id);
    boolean employeeNoCheck(Long employeeNo);

    ResponseEmployeeDto getEmployeeByNo(Long employeeNo);

    ResponseListEmployeeDto getEmployeeList(EmployeeSearchDto dto, int page, int size);

    void updateId(Long employeeNo, String id);
    void updateName(Long employeeNo, String name);
    void updatePassword(Long employeeNo, String password);
    void updateImg(Long employeeNo, String img);
    void updateTel(Long employeeNo, String tel);
    void updateEmail(Long employeeNo, String email);

    void removeEmployee(Long employeeNo);
}
