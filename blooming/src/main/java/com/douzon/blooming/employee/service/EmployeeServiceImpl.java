package com.douzon.blooming.employee.service;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.exception.EmployeePermissionDefinedException;
import com.douzon.blooming.auth.provider.TokenProvider;
import com.douzon.blooming.employee.dto.request.AuthUpdateEmployeeDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.exception.EmployeeNotFoundException;
import com.douzon.blooming.employee.exception.PasswordDoesNotMatchException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import com.douzon.blooming.product.exception.NotFoundProductException;
import io.jsonwebtoken.security.Password;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
  public ResponseListEmployeeDto getEmployeeList(EmployeeSearchDto dto) {
    if (dto.getPage() != 0) {
      dto.setPage(dto.getPage() - 1);
    }

    List<ListEmployeeDto> employeeList = employeeRepository.findEmployeeListWithFilter(dto);

    int searchEmployeeCount = employeeRepository.getCountEmployees(dto);
    int start = dto.getPage() * dto.getSize();

    boolean hasNextPage = start + dto.getSize() < searchEmployeeCount;
    boolean hasPreviousPage = start > 0;

    return new ResponseListEmployeeDto(employeeList, dto.getPage() + 1, hasNextPage,
        hasPreviousPage);
  }

  @Override
  public void updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long employeeNo) {
    ResponseEmployeeDto responseEmployeeDto = employeeRepository.findEmployeeByNo(employeeNo)
        .orElseThrow(NotFoundProductException::new);
    if (!responseEmployeeDto.getPassword().equals(updateEmployeeDto.getOldPassword())) {
      throw new PasswordDoesNotMatchException();
    }
    employeeRepository.updateEmployeeByUpdateEmployeeDto(updateEmployeeDto, employeeNo);
  }

  @Override
  public void updateEmployee(AuthUpdateEmployeeDto authUpdateEmployeeDto, Long employeeNo) {
    employeeRepository.updateEmployeeByAuthUpdateEmployeeDto(authUpdateEmployeeDto, employeeNo);
  }

  @Override
  public void signup(InsertEmployeeDto employeeDto) {
    employeeRepository.insertEmployee(employeeDto.encodingPassword(passwordEncoder));
  }

  @Override
  public void removeEmployee(Long employeeNo) {
    employeeRepository.deleteEmployee(employeeNo);
  }

  @Override
  public TokenDto login(LoginEmployeeDto loginEmployeeDto) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginEmployeeDto.getId(), loginEmployeeDto.getPassword());
    Authentication authenticate = managerBuilder.getObject().authenticate(token);

    return tokenProvider.generateToken(authenticate);
  }
}
