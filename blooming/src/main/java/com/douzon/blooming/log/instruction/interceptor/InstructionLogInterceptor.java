package com.douzon.blooming.log.instruction.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.instruction.dto.InstructionLogDto;
import com.douzon.blooming.log.instruction.service.InstructionLogService;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InstructionLogInterceptor extends AbstractLogInterceptor<InstructionLogDto> {

  public InstructionLogInterceptor(InstructionLogService instructionLogService, KafkaProducerService kafkaProducerService) {
    super(instructionLogService, kafkaProducerService);
  }

  @Override
  public InstructionLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String target = getTarget(req);
    String message = target.isBlank()? "새로운 지시를 " :  "지시(지시번호: " + target + ")를 ";
    kafkaProducerService.sendCRUDEvent(employeeDetails.getEmployeeNo() + "("+employeeDetails.getName()+ "), " + message + getVerb(req));
    return InstructionLogDto.builder()
        .ipAddress(getClientIp(req))
        .instructionNo(target.isBlank()? null:target)
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
