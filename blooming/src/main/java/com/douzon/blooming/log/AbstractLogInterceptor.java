package com.douzon.blooming.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class AbstractLogInterceptor<T> implements LogInterceptor<T> {

  private final LogService<T> logService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {
    if (!request.getMethod().equalsIgnoreCase("GET")) {
      T dto = getLogDto(request);
      logService.addLog(dto);
    }
  }

  protected String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
