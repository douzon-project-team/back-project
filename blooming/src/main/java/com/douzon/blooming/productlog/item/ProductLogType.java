package com.douzon.blooming.productlog.item;

import lombok.Getter;

public enum ProductLogType {
  SELECT("GET"), INSERT("POST"), UPDATE("PUT"), DELETE("DELETE"), NU_KNOWN("UN_KNOWN");

  @Getter
  private final String method;

  ProductLogType(String method) {
    this.method = method;
  }

  public static ProductLogType fromRequestMethod(String method) {
    for (ProductLogType type : values()) {
      if (type.method.equalsIgnoreCase(method)) {
        return type;
      }
    }
    return NU_KNOWN;
  }
}
