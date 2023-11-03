package com.douzon.blooming.employee.dto.request;

import com.douzon.blooming.auth.EmployeeRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeSearchDto {

  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_SIZE = 8;

  private Long employeeNo;
  private String name;
  private EmployeeRole role;
  @Setter
  private Integer page = DEFAULT_PAGE;
  private Integer size = DEFAULT_SIZE;
}