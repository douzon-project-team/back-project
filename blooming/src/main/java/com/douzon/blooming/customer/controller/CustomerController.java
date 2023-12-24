package com.douzon.blooming.customer.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.DuplicateRequestDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<Void> addCustomer(@Valid @RequestBody RequestCustomerDto dto){
        service.addCustomer(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{customerCode}")
    public ResponseEntity<?> duplicateCheckCustomerCode(@PathVariable String customerCode){
        return ResponseEntity.ok().body(service.customerCodeCheck(customerCode));
    }

    @GetMapping("/list")
    public ResponseEntity<PageDto<ResponseCustomerDto>> getCustomers(@ModelAttribute CustomerSearchDto dto){
        PageDto<ResponseCustomerDto> customer = service.findCustomers(dto);
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/{customerNo}")
    public ResponseEntity<ResponseCustomerDto> getCustomer(@PathVariable Long customerNo){
        ResponseCustomerDto customer = service.findCustomer(customerNo);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/{customerNo}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerNo,
                                                @RequestBody @Valid UpdateCustomerDto dto){
        service.updateCustomer(dto, customerNo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerNo}")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long customerNo){
        service.removeCustomer(customerNo);
        return ResponseEntity.noContent().build();
    }
}
