package com.douzon.blooming.log;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

public interface LogInterceptor<T> extends HandlerInterceptor {

  T getLogDto(HttpServletRequest req);
}
