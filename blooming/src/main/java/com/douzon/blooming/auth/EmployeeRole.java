package com.douzon.blooming.auth;

import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
import java.util.Arrays;

public enum EmployeeRole {
  ROLE_ADMIN, ROLE_MEMBER;

  public static EmployeeRole fromRoleName(String name) {
    return Arrays.stream(values())
        .filter(employeeRole -> employeeRole.name().equalsIgnoreCase(name))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Invalid employee role name :" + name));
  }

  public static EmployeeRole fromOrdinal(int ordinal) {
    return Arrays.stream(EmployeeRole.values()).filter(role -> role.ordinal() == ordinal)
        .findAny()
        .orElseThrow(UnsupportedEmployeeRoleException::new);
  }
}
