package com.douzon.blooming.employee.dto.request;

import com.douzon.blooming.SearchDto;
import com.douzon.blooming.auth.EmployeeRole;
import lombok.Getter;

@Getter
public class EmployeeSearchDto extends SearchDto {

  private final String name;
  private final Long employeeNo;
  private final EmployeeRole role;

  public EmployeeSearchDto(Long employeeNo, String name, EmployeeRole role, Integer pageSize,
      Integer page) {
    super(pageSize, page);
    this.employeeNo = employeeNo;
    this.name = name == null ? DEFAULT_STRING : name;
    this.role = role;
  }
}