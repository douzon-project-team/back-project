package com.douzon.blooming;

import com.douzon.blooming.log.delivery.interceptor.DeliveryLogInterceptor;
import com.douzon.blooming.log.instruction.interceptor.InstructionLogInterceptor;
import com.douzon.blooming.log.product.interceptor.ProductLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final ProductLogInterceptor productLogInterceptor;
  private final DeliveryLogInterceptor deliveryLogInterceptor;
  private final InstructionLogInterceptor instructionLogInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(productLogInterceptor)
        .addPathPatterns("/products/**");
    registry.addInterceptor(deliveryLogInterceptor)
        .addPathPatterns("/delivery/**");
    registry.addInterceptor(instructionLogInterceptor)
        .addPathPatterns("/instruction/**");
  }
}
