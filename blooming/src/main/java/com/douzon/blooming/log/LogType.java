package com.douzon.blooming.log;

import java.util.Arrays;
import lombok.Getter;

public enum LogType {
  SELECT("GET"), INSERT("POST"), UPDATE("PUT"), DELETE("DELETE"), NU_KNOWN("UN_KNOWN");

  @Getter
  private final String method;

  LogType(String method) {
    this.method = method;
  }

  public static LogType fromRequestMethod(String method) {
    return Arrays.stream(values())
        .filter(logType -> logType.method.equalsIgnoreCase(method))
        .findAny()
        .orElse(NU_KNOWN);
  }
}
