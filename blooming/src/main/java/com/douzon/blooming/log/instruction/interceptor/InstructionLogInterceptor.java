package com.douzon.blooming.log.instruction.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.instruction.dto.InstructionLogDto;
import com.douzon.blooming.log.instruction.service.InstructionLogService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class InstructionLogInterceptor implements HandlerInterceptor {

  private final InstructionLogService instructionLogService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {
    if (!request.getMethod().equalsIgnoreCase("GET")) {
      InstructionLogDto instructionLog = getInstructionLog(request);
      instructionLogService.addInstructionLog(instructionLog);
    }
  }

  private InstructionLogDto getInstructionLog(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return InstructionLogDto.builder()
        .idAddress(getClientIp(req))
        .instructionNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }

  private String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
