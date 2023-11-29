package com.douzon.blooming.customer.repo;

import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.main.dto.request.MainPageCustomerDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CustomerRepository {

    boolean customerCodeCheck(String customerCode);

    void insertCustomer(RequestCustomerDto dto);

    Optional<ResponseCustomerDto> findCustomer(Long customerNo);

    Integer getCountCustomers(String customerName);

    List<ResponseCustomerDto> findCustomers(@Param("dto") CustomerSearchDto dto, int start, int pageSize);

    int updateCustomer(@Param("dto")UpdateCustomerDto dto, Long customerNo);

    int updateCustomerHide(Long customerNo);

    List<MainPageCustomerDto> findManyTransactionCustomers();
}
