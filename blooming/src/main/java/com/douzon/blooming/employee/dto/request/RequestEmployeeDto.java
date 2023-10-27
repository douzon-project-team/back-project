package com.douzon.blooming.employee.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestEmployeeDto {

  private Integer employeeNo;
  private String id;
  private String password;
  private String name;
  private String img;
  private Long role;
  private String tel;
  private String email;
}
