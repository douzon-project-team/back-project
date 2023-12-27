package com.douzon.blooming.log.product.interceptor;


import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.product.dto.ProductLogDto;
import com.douzon.blooming.log.product.service.ProductLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductLogInterceptor extends AbstractLogInterceptor<ProductLogDto> {

  public ProductLogInterceptor(ProductLogService productLogService, KafkaProducerService kafkaProducerService) {
    super(productLogService, kafkaProducerService);
  }

  @Override
  public ProductLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String target = getTarget(req);
    String message = target.isBlank()? "새로운 품목을 " :  "품목(품목번호: " + target + ")을 ";
    Runnable runnable = () -> kafkaProducerService.sendCRUDEvent(employeeDetails.getEmployeeNo() + "("+employeeDetails.getName()+ "), " + message + getVerb(req));
    try {
      new Thread(runnable).start();
    }catch (Exception ignore) {}
    return ProductLogDto.builder()
        .ipAddress(getClientIp(req))
        .productNo(target.isBlank()?null: Long.valueOf(target))
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
