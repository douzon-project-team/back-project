package com.douzon.blooming.customer.controller;

import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseCustomerDto;
import com.douzon.blooming.customer.dto.response.ResponseListCustomerDto;
import com.douzon.blooming.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @PostMapping("/insert")
    public ResponseEntity<Void> insertCustomer(RequestCustomerDto dto){
        service.insertCustomer(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseListCustomerDto> getCustomerList(@RequestBody String customerName,
                                                                   @RequestParam(defaultValue = "1") Integer page,
                                                                   @RequestParam(defaultValue = "8") Integer pageSize){
        return new ResponseEntity<ResponseListCustomerDto>(service.getCustomerList(customerName, page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{customerNo}")
    public ResponseEntity<ResponseCustomerDto> getCustomer(@PathVariable Long customerNo){
        return new ResponseEntity<ResponseCustomerDto>(service.getCustomer(customerNo), HttpStatus.OK);
    }

    @PutMapping("/{customerNo}/update")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerNo,
                                               @RequestBody UpdateCustomerDto dto){
        service.updateCustomer(dto, customerNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{customerNo}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerNo){
        service.deleteCustomer(customerNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
