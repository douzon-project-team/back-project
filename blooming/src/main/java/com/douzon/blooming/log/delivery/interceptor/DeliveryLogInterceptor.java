package com.douzon.blooming.log.delivery.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import com.douzon.blooming.log.delivery.service.DeliveryLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DeliveryLogInterceptor extends AbstractLogInterceptor<DeliveryLogDto> {

  public DeliveryLogInterceptor(DeliveryLogService deliveryLogService) {
    super(deliveryLogService);
  }

  @Override
  public DeliveryLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return DeliveryLogDto.builder()
        .idAddress(getClientIp(req))
        .deliveryNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
