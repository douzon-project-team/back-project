package com.douzon.blooming.customer.service;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseListCustomerDto;

public interface CustomerService {
    String customerCodeCheck(String customerCode);
    void addCustomer(RequestCustomerDto dto);
    ResponseCustomerDto getCustomer(Long customerNo);
    ResponseListCustomerDto getCustomerList(String customerCode, int page, int pageSize);
    void updateCustomer(UpdateCustomerDto dto, Long customerNo);
    void removeCustomer(Long customerNo);
}
