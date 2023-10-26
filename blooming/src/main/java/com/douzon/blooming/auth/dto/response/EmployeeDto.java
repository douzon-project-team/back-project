package com.douzon.blooming.auth.dto.response;


import com.douzon.blooming.auth.EmployeeRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeDto {

  private Long employeeNo;
  private String id;
  private String password;
  private EmployeeRole role;
}
