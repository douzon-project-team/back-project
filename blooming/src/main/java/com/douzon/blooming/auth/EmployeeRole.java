package com.douzon.blooming.auth;

import java.util.Arrays;

public enum EmployeeRole {
  ROLE_ADMIN, ROLE_MEMBER;

  public static EmployeeRole fromRoleName(String name) {
    return Arrays.stream(values())
        .filter(employeeRole -> employeeRole.name().equalsIgnoreCase(name))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Invalid employee role name :" + name));
  }
}
