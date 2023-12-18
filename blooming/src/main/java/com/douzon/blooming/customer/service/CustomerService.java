package com.douzon.blooming.customer.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseDuplicateResultDto;

public interface CustomerService {
    ResponseDuplicateResultDto customerCodeCheck(String customerCode);
    void addCustomer(RequestCustomerDto dto);
    ResponseCustomerDto findCustomer(Long customerNo);
    PageDto<ResponseCustomerDto> findCustomers(CustomerSearchDto dto);
    void updateCustomer(UpdateCustomerDto dto, Long customerNo);
    void removeCustomer(Long customerNo);
}
