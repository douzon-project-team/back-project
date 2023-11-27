package com.douzon.blooming.log.employee.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.employee.dto.EmployeeLogDto;
import com.douzon.blooming.log.employee.service.EmployeeLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLogInterceptor extends AbstractLogInterceptor<EmployeeLogDto> {

  public EmployeeLogInterceptor(EmployeeLogService employeeLogService, KafkaProducerService kafkaProducerService) {
    super(employeeLogService, kafkaProducerService);
  }

  @Override
  public EmployeeLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return EmployeeLogDto.builder()
        .ipAddress(getClientIp(req))
        .employeeNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
