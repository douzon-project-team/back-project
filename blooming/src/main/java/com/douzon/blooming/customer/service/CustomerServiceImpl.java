package com.douzon.blooming.customer.service;

import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseListCustomerDto;
import com.douzon.blooming.customer.exception.NotFoundCustomerException;
import com.douzon.blooming.customer.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository repository;

    @Override
    public boolean customerCodeCheck(String customerCode) {
        return repository.customerCodeCheck(customerCode);
    }

    @Override
    public void addCustomer(RequestCustomerDto dto) {
        repository.insertCustomer(dto);
    }

    @Override
    public ResponseCustomerDto findCustomer(Long customerNo) {
        return repository.findCustomer(customerNo)
                .orElseThrow(NotFoundCustomerException::new);
    }

    @Override
    public ResponseListCustomerDto findCustomers(CustomerSearchDto dto) {
        int start = (dto.getPage() - 1) * dto.getPageSize();
        List<ResponseCustomerDto> customerList = repository.findCustomers(dto.getCustomerName(), start, dto.getPageSize());
        if(customerList.isEmpty()){
            throw new NotFoundCustomerException();
        }
        int searchCustomerCount = repository.getCountCustomers(dto.getCustomerName());

        boolean hasNextPage = (start + dto.getPageSize()) < searchCustomerCount;
        boolean hasPreviousPage = start > 0;

        return new ResponseListCustomerDto(customerList, dto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateCustomer(UpdateCustomerDto dto, Long customerNo) {
        if(repository.updateCustomer(dto, customerNo) <= 0){
            throw new NotFoundCustomerException();
        }
    }

    @Override
    public void removeCustomer(Long customerNo) {
        if(repository.deleteCustomer(customerNo) <= 0){
            throw new NotFoundCustomerException();
        }
    }
}
