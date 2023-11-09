package com.douzon.blooming.customer.service;

import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseListCustomerDto;

public interface CustomerService {
    boolean customerCodeCheck(String customerCode);
    void addCustomer(RequestCustomerDto dto);
    ResponseCustomerDto findCustomer(Long customerNo);
    ResponseListCustomerDto findCustomers(CustomerSearchDto dto);
    void updateCustomer(UpdateCustomerDto dto, Long customerNo);
    void removeCustomer(Long customerNo);

}
