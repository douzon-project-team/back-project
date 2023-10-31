package com.douzon.blooming.log.delivery.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import com.douzon.blooming.log.delivery.service.DeliveryLogService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class DeliveryLogInterceptor implements HandlerInterceptor {

  private final DeliveryLogService deliveryLogService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {
    if (!request.getMethod().equalsIgnoreCase("GET")) {
      DeliveryLogDto deliveryLog = getDeliveryLog(request);
      deliveryLogService.addDeliveryLog(deliveryLog);
    }
  }

  private DeliveryLogDto getDeliveryLog(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return DeliveryLogDto.builder()
        .idAddress(getClientIp(req))
        .deliveryNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }

  private String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
