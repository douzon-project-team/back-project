package com.douzon.blooming.log.delivery.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import com.douzon.blooming.log.delivery.service.DeliveryLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DeliveryLogInterceptor extends AbstractLogInterceptor<DeliveryLogDto> {

  public DeliveryLogInterceptor(DeliveryLogService deliveryLogService, KafkaProducerService kafkaProducerService) {
    super(deliveryLogService, kafkaProducerService);
  }

  @Override
  public DeliveryLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String target = getTarget(req);
    String message = target.isBlank()? "새로운 출고를 " :  "출고(출고번호: " + target + ")을 ";
    Runnable runnable = () -> kafkaProducerService.sendCRUDEvent(employeeDetails.getEmployeeNo() + "("+employeeDetails.getName()+ "), " + message + getVerb(req));
    try {
      new Thread(runnable).start();
    }catch (Exception ignore) {}
    return DeliveryLogDto.builder()
        .ipAddress(getClientIp(req))
        .deliveryNo(target.isBlank()?null:target)
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
