package com.douzon.blooming.auth.dto.request;

import com.douzon.blooming.auth.item.EmployeeRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InsertEmployeeDto {

  private String id;
  @Setter
  private String password;
  private String name;
  private String img;
  private EmployeeRole role;
  private String tel;
  private String email;

  public InsertEmployeeDto encodingPassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(password);
    return this;
  }
}
