package com.douzon.blooming.log.customer.interceptor;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.log.AbstractLogInterceptor;
import com.douzon.blooming.log.customer.dto.CustomerLogDto;
import com.douzon.blooming.log.customer.service.CustomerLogService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
@Component
public class CustomerLogInterceptor extends AbstractLogInterceptor<CustomerLogDto> {

    public CustomerLogInterceptor(CustomerLogService customerLogService, KafkaProducerService kafkaProducerService) {
        super(customerLogService, kafkaProducerService);
    }

    @Override
    public CustomerLogDto getLogDto(HttpServletRequest req) {
        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String target = getTarget(req);
        String message = target.isBlank()? "새로운 거래처를 " :  "거래처(거래처번호: " + target + ")을 ";
        Runnable runnable = () ->  kafkaProducerService.sendCRUDEvent(employeeDetails.getEmployeeNo() + "("+employeeDetails.getName()+ "), " + message + getVerb(req));
        try {
            new Thread(runnable).start();
        }catch (Exception ignore) {}
        return null;
    }
}
