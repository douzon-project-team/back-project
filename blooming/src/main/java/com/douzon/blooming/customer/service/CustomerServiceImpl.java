package com.douzon.blooming.customer.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseDuplicateResultDto;
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
    public ResponseDuplicateResultDto customerCodeCheck(String customerCode) {
        ResponseDuplicateResultDto dto = new ResponseDuplicateResultDto(!repository.customerCodeCheck(customerCode));
        return dto;
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
    public PageDto<ResponseCustomerDto> findCustomers(CustomerSearchDto dto) {
        int start = dto.getPage() * dto.getPageSize();
        List<ResponseCustomerDto> customerList = repository.findCustomers(dto, start, dto.getPageSize());
        int searchCustomerCount = repository.getCountCustomers(dto);

        boolean hasNextPage = (start + dto.getPageSize()) < searchCustomerCount;
        boolean hasPreviousPage = start > 0;

        return new PageDto<>(customerList, dto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void updateCustomer(UpdateCustomerDto dto, Long customerNo) {
        if(repository.updateCustomer(dto, customerNo) <= 0){
            throw new NotFoundCustomerException();
        }
    }

    @Override
    public void removeCustomer(Long customerNo) {
        if(repository.updateCustomerHide(customerNo) <= 0){
            throw new NotFoundCustomerException();
        }
    }
}
