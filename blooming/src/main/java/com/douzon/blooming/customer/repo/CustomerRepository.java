package com.douzon.blooming.customer.repo;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerRepository {

    String customerCodeCheck(String customerCode);

    void insertCustomer(RequestCustomerDto dto);

    ResponseCustomerDto getCustomer(Long customerNo);

    Integer getCountCustomers(String customerName);

    List<ResponseCustomerDto> getCustomerList(String customerName, int start, int pageSize);

    void updateCustomer(@Param("dto")UpdateCustomerDto dto, Long customerNo);

    void deleteCustomer(Long customerNo);
}
