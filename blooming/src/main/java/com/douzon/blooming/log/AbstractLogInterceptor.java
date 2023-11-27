package com.douzon.blooming.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.exception.MethodNotSupportedException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@AllArgsConstructor
public abstract class AbstractLogInterceptor<T> implements LogInterceptor<T> {

  private final LogService<T> logService;
  protected final KafkaProducerService kafkaProducerService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {

    if (!request.getMethod().equalsIgnoreCase("GET")) {
      T dto = getLogDto(request);
      logService.addLog(dto);
    }
  }

  protected String getTarget(HttpServletRequest request) {
    if(!request.getMethod().equalsIgnoreCase("POST")){
      String[] splitUrI = request.getRequestURI().split("/");
      return splitUrI[2];
    }
    return "";
  }

  protected String getVerb(HttpServletRequest request){
    switch(request.getMethod()){
      case "POST":
        return "등록하였습니다.";
      case "PUT":
        return "수정하였습니다.";
      case "DELETE":
        return "삭제하였습니다.";
      default:
        throw new MethodNotSupportedException(request.getMethod());
    }
  }

  protected String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
