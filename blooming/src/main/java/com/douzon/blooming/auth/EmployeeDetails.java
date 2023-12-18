package com.douzon.blooming.auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EmployeeDetails implements UserDetails {

  private Long employeeNo;
  private String id;
  private String name;
  private String password;
  private EmployeeRole role;

  public EmployeeDetails(Long employeeNo, String id, String name, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.employeeNo = employeeNo;
    this.id = id;
    this.name = name;
    this.password = password;
    this.role = EmployeeRole.fromRoleName(authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(",")));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  public Long getEmployeeNo() {
    return this.employeeNo;
  }

  public String getName() {return this.name;}

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.id;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
