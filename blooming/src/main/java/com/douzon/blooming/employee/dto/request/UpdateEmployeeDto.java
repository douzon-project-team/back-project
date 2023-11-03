package com.douzon.blooming.employee.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateEmployeeDto {

  @NotBlank
  private String oldPassword;

  @NotBlank
  private String password;

  @NotBlank
  private String name;

  @Pattern(regexp = "^\\d{11}$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
  private String tel;

  @Email
  private String email;
}
