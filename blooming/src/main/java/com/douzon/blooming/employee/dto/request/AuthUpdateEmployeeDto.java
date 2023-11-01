package com.douzon.blooming.employee.dto.request;

import com.douzon.blooming.auth.EmployeeRole;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthUpdateEmployeeDto {

  @NotNull
  @Pattern(regexp = "^\\d{6}$")
  private Long employeeNo;

  @Pattern(regexp = "^[a-zA-Z0-9]{4,}$")
  @NotBlank(message = "아이디는 필수 값입니다.")
  private String id;

  @Setter
  @NotBlank(message = "비밀번호는 필수 값입니다.")
  private String password;

  @NotBlank(message = "이름은 필수 값입니다.")
  private String name;

  private EmployeeRole role;

  @Pattern(regexp = "^\\d{11}$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
  private String tel;

  @Email(message = "이메일 형식이 아닙니다.")
  private String email;
}

