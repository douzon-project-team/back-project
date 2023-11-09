package com.douzon.blooming.employee.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseEmployeeDto {

  private Long employeeNo;
  private String id;
  private String password;
  private String name;
  private String tel;
  private String email;
}
