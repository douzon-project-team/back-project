package com.douzon.blooming.log;

import com.douzon.blooming.log.exception.UnsupportedLogTypeException;
import java.util.Arrays;
import lombok.Getter;

public enum LogType {
  INSERT("POST"), UPDATE("PUT"), DELETE("DELETE");

  @Getter
  private final String method;

  LogType(String method) {
    this.method = method;
  }

  public static LogType fromRequestMethod(String method) {
    return Arrays.stream(values())
        .filter(logType -> logType.method.equalsIgnoreCase(method))
        .findAny()
        .orElseThrow(UnsupportedLogTypeException::new);
  }
  }
