package com.douzon.blooming.employee.dto.response;

import com.douzon.blooming.auth.EmployeeRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ListEmployeeDto {

  private Long employeeNo;
  private String name;
  private EmployeeRole role;
  private String tel;
  private String email;
}
