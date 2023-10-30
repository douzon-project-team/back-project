package com.douzon.blooming.employee.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginEmployeeDto {

  @NotBlank(message = "아이디는 필수 값입니다.")
  private String id;
  @NotBlank(message = "비밀번호는 필수 값입니다.")
  private String password;
}
