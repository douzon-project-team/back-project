package com.douzon.blooming.employee.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeSearchDto {

  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_SIZE = 8;

  private Long employeeNo;
  private String name;
  private Long role;
  private Integer page = DEFAULT_PAGE;
  private Integer size = DEFAULT_SIZE;
}