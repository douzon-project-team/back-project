package com.douzon.blooming.employee.service;

import com.douzon.blooming.auth.dto.request.InsertEmployeeDto;
import com.douzon.blooming.auth.dto.request.LoginEmployeeDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.provider.TokenProvider;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.exception.EmployeeNotFoundException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  private final AuthenticationManagerBuilder managerBuilder;
  private final EmployeeRepository employeeRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  @Override
  public boolean employeeNoCheck(Long employeeNo) {
    return employeeRepository.existByEmployeeNo(employeeNo);
  }

  @Override
  public boolean idCheck(String id) {
    return employeeRepository.existById(id);
  }

  @Override
  public ResponseEmployeeDto getEmployeeByNo(Long employeeNo) {
    return employeeRepository.findEmployeeByNo(employeeNo)
        .orElseThrow(EmployeeNotFoundException::new);
  }

  @Override
  public ResponseListEmployeeDto getEmployeeList(EmployeeSearchDto dto, int page, int pageSize) {
    int start = (page - 1) * pageSize;
    List<ListEmployeeDto> employeeList = employeeRepository.findEmployeeListWithFilter(dto, start,
        pageSize);
    int searchEmployeeCount = employeeRepository.getCountEmployees(dto);

    boolean hasNextPage = start + page < searchEmployeeCount;
    boolean hasPreviousPage = start > 0;

    return new ResponseListEmployeeDto(employeeList, page, hasNextPage, hasPreviousPage);
  }

  @Override
  public void updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long employeeNo) {
    employeeRepository.updateEmployeeByUpdateEmployeeDto(updateEmployeeDto, employeeNo);
  }

  public void signup(InsertEmployeeDto employeeDto) {
    employeeRepository.insertEmployee(employeeDto.encodingPassword(passwordEncoder));
  }

  public void removeEmployee(Long employeeNo) {
    employeeRepository.deleteEmployee(employeeNo);
  }

  public TokenDto login(LoginEmployeeDto loginEmployeeDto) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginEmployeeDto.getId(), loginEmployeeDto.getPassword());
    Authentication authenticate = managerBuilder.getObject().authenticate(token);

    return tokenProvider.generateToken(authenticate);
  }
}
