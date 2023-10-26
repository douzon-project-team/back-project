package com.douzon.blooming.log.employee.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.employee.dto.EmployeeLogDto;
import com.douzon.blooming.log.employee.service.EmployeeLogService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class EmployeeLogInterceptor implements HandlerInterceptor {

  private final EmployeeLogService employeeLogService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {
    if (!request.getMethod().equalsIgnoreCase("GET")) {
      EmployeeLogDto employeeLog = getEmployeeLog(request);
      employeeLogService.addEmployeeLog(employeeLog);
    }
  }

  private EmployeeLogDto getEmployeeLog(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return EmployeeLogDto.builder()
        .ipAddress(getClientIp(req))
        .employeeNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }

  private String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
