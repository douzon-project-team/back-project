package com.douzon.blooming.productlog.item;

import lombok.Getter;

public enum ProductLogType {
  SELECT("GET"), INSERT("PUT"), UPDATE("UPDATE"), DELETE("DELETE");

  @Getter
  private final String method;

  ProductLogType(String method) {
    this.method = method;
  }

  public static ProductLogType fromMethod(String method) {
    for (ProductLogType type : values()) {
      if (type.method.equalsIgnoreCase(method)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid method: " + method);
  }
}
