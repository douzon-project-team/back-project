package com.douzon.blooming.log.customer.service;

import com.douzon.blooming.log.LogService;
import com.douzon.blooming.log.customer.dto.CustomerLogDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerLogService implements LogService<CustomerLogDto> {
    @Override
    public void addLog(CustomerLogDto customerLogDto) {

    }
}
