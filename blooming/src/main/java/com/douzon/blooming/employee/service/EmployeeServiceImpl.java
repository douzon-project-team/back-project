package com.douzon.blooming.employee.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.employee.dto.request.AuthUpdateEmployeeDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.EmployeeListDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.exception.NotFoundEmployeeException;
import com.douzon.blooming.employee.exception.PasswordDoesNotMatchException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import com.douzon.blooming.product.exception.NotFoundProductException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final PasswordEncoder passwordEncoder;

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
        .orElseThrow(NotFoundEmployeeException::new);
  }

  @Override
  public PageDto<EmployeeListDto> getEmployeeList(EmployeeSearchDto dto) {
    int start = dto.getPage() * dto.getPageSize();

    List<EmployeeListDto> employeeList = employeeRepository.findAllByEmployeeSearchDto(dto, start);

    int count = employeeRepository.getEmployeesCountBySearchEmployeeDto(dto);

    return PageDto.<EmployeeListDto>builder()
        .list(employeeList)
        .currentPage(dto.getPage() + 1)
        .hasNextPage(start + dto.getPageSize() < count)
        .hasPreviousPage(start > 0)
        .build();
  }

  @Override
  public void updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long employeeNo) {
    ResponseEmployeeDto responseEmployeeDto = employeeRepository.findEmployeeByNo(employeeNo)
        .orElseThrow(NotFoundProductException::new);
    if (updateEmployeeDto.getOldPassword() != null && !updateEmployeeDto.getOldPassword().isBlank()
        && !responseEmployeeDto.getPassword().equals(updateEmployeeDto.getOldPassword())) {
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
  public Long findEmployeeNoByDto(LoginEmployeeDto loginEmployeeDto) {
    Long employeeNo = employeeRepository.findEmployeeNoByDto(loginEmployeeDto);
    if (employeeNo == 0) {
      throw new com.douzon.blooming.auth.exception.NotFoundEmployeeException();
    }
    return employeeNo;
  }
}
