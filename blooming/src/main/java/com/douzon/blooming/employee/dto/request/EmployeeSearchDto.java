package com.douzon.blooming.employee.dto.request;

import com.douzon.blooming.SearchDto;
import com.douzon.blooming.auth.EmployeeRole;
import lombok.Getter;

@Getter
public class EmployeeSearchDto extends SearchDto {

  private final String name;
  private Long employeeNo;
  private EmployeeRole role;

  protected EmployeeSearchDto() {
    super();
    this.name = DEFAULT_STRING;
  }

  public EmployeeSearchDto(Long employeeNo, String name, EmployeeRole role, Integer pageSize,
      Integer page) {
    super(pageSize, page);
    this.employeeNo = employeeNo;
    this.name = name == null ? DEFAULT_STRING : name;
    this.role = role;
  }
}