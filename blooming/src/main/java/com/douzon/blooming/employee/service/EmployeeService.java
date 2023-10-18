package com.douzon.blooming.employee.service;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;

import java.util.List;

public interface EmployeeService {
    boolean login(LoginDto dto);

    void insertEmployee(RequestEmployeeDto dto);

    String idCheck(String id);
    boolean employeeNoCheck(Long employeeNo);

    List<ResponseListEmployeeDto> getEmployeeListWithFilter(EmployeeSearchDto dto);

    void updateId(Long employeeNo, String id);
    void updateName(Long employeeNo, String name);
    void updatePassword(Long employeeNo, String password);
    void updateImg(Long employeeNo, String img);
    void updateTel(Long employeeNo, String tel);
    void updateEmail(Long employeeNo, String email);

    void deleteEmployee(Long employeeNo);
}
