package com.douzon.blooming;

import com.douzon.blooming.employee.interceptor.EmployeeCheckInterceptor;
import com.douzon.blooming.log.customer.interceptor.CustomerLogInterceptor;
import com.douzon.blooming.log.delivery.interceptor.DeliveryLogInterceptor;
import com.douzon.blooming.log.employee.interceptor.*;
import com.douzon.blooming.log.instruction.interceptor.InstructionLogInterceptor;
import com.douzon.blooming.log.product.interceptor.ProductLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final ProductLogInterceptor productLogInterceptor;
  private final DeliveryLogInterceptor deliveryLogInterceptor;
  private final InstructionLogInterceptor instructionLogInterceptor;
  private final EmployeeLogInterceptor employeeLogInterceptor;
  private final  CustomerLogInterceptor customerLogInterceptor;

  private final EmployeeCheckInterceptor employeeCheckInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(productLogInterceptor)
//        .addPathPatterns("/products/**", "/products");
//    registry.addInterceptor(customerLogInterceptor)
//            .addPathPatterns("/customers/**", "/customers");
//    registry.addInterceptor(deliveryLogInterceptor)
//        .addPathPatterns("/deliveries/**", "/deliveries");
//    registry.addInterceptor(instructionLogInterceptor)
//        .addPathPatterns("/instructions/**", "/instructions");
//    registry.addInterceptor(employeeLogInterceptor)
//        .addPathPatterns("/employees/**", "/employees")
//        .excludePathPatterns("/employees/login", "/employees/logout");
//    registry.addInterceptor(employeeCheckInterceptor)
//        .addPathPatterns("/employees/**");
  }
}
