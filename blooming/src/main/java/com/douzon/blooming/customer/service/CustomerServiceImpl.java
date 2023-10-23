package com.douzon.blooming.customer.service;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseListCustomerDto;
import com.douzon.blooming.customer.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository repository;

    @Override
    public void insertCustomer(RequestCustomerDto dto) {
        repository.insertCustomer(dto);
    }

    @Override
    public ResponseCustomerDto getCustomer(Long customerNo) {
        return repository.getCustomer(customerNo);
    }

    @Override
    public ResponseListCustomerDto getCustomerList(String customerName, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        List<ResponseCustomerDto> customerList = repository.getCustomerList(customerName, start, pageSize);
        int searchCustomerCount = repository.getCountCustomers(customerName);

        boolean hasNextPage = start + page < searchCustomerCount;
        boolean hasPreviousPage = start > 0;

        return new ResponseListCustomerDto(customerList, page, hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateCustomer(UpdateCustomerDto dto, Long customerNo) {
        repository.updateCustomer(dto, customerNo);
    }

    @Override
    public void deleteCustomer(Long customerNo) {
        repository.deleteCustomer(customerNo);
    }
}
