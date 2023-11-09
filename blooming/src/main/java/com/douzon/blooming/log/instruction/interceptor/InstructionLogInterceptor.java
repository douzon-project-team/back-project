package com.douzon.blooming.log.instruction.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.instruction.dto.InstructionLogDto;
import com.douzon.blooming.log.instruction.service.InstructionLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InstructionLogInterceptor extends AbstractLogInterceptor<InstructionLogDto> {

  public InstructionLogInterceptor(InstructionLogService instructionLogService) {
    super(instructionLogService);
  }

  @Override
  public InstructionLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return InstructionLogDto.builder()
        .ipAddress(getClientIp(req))
        .instructionNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
