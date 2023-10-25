package com.douzon.blooming.productlog.interceptor;


import com.douzon.blooming.auth.item.EmployeeDetails;
import com.douzon.blooming.productlog.dto.ProductLogDto;
import com.douzon.blooming.productlog.item.ProductLogType;
import com.douzon.blooming.productlog.service.ProductLogService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ProductLogInterceptor implements HandlerInterceptor {

  private final ProductLogService productLogService;

  @Override
  public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull Object handler, Exception ex) {
    if (!request.getMethod().equalsIgnoreCase("GET")) {
      ProductLogDto productLog = getProductLog(request);
      productLogService.addProductLog(productLog);
    }
  }

  private ProductLogDto getProductLog(HttpServletRequest req) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return ProductLogDto.builder()
        .idAddress(getClientIp(req))
        .productNo(1L) // 나중에 Refactor 필요
        .modifierNo(employeeDetails.getEmployeeNo())
        .type(ProductLogType.fromRequestMethod(req.getMethod()))
        .build();
  }

  private String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
  }
}
