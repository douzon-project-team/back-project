package com.douzon.blooming.employee.service;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.AuthUpdateEmployeeDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {


  boolean employeeNoCheck(Long employeeNo);

  boolean idCheck(String id);

  ResponseEmployeeDto getEmployeeByNo(Long employeeNo);

  ResponseListEmployeeDto getEmployeeList(EmployeeSearchDto dto);

  void updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long employeeNo);

  void updateEmployee(AuthUpdateEmployeeDto authUpdateEmployeeDto, Long employeeNo);

  void signup(InsertEmployeeDto employeeDto);

  void removeEmployee(Long employeeNo);

  TokenDto login(LoginEmployeeDto loginEmployeeDto);
}
