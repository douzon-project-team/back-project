package com.douzon.blooming.log.product.interceptor;


import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.LogType;
import com.douzon.blooming.log.product.dto.ProductLogDto;
import com.douzon.blooming.log.product.service.ProductLogService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ProductLogInterceptor extends AbstractLogInterceptor<ProductLogDto> {

  public ProductLogInterceptor(ProductLogService productLogService) {
    super(productLogService);
  }

  @Override
  public ProductLogDto getLogDto(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return ProductLogDto.builder()
        .ipAddress(getClientIp(req))
        .productNo(1L) // TODO : 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(LogType.fromRequestMethod(req.getMethod()))
        .build();
  }
}
