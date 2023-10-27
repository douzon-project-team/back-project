package com.douzon.blooming.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeSearchDto {

  private Long employeeNo;
  private String name;
  private Long role;
}