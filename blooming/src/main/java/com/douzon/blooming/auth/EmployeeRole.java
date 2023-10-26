package com.douzon.blooming.auth;

public enum EmployeeRole {
  ROLE_ADMIN, ROLE_MEMBER;

  public static EmployeeRole fromRoleName(String name) {
    for (EmployeeRole type : values()) {
      if (type.name().equalsIgnoreCase(name)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid name: " + name);
  }
}
