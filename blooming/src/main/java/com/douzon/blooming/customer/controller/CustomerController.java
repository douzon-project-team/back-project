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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @PostMapping("/insert")
    public ResponseEntity<Void> insertCustomer(@RequestBody RequestCustomerDto dto){
        service.addCustomer(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/customer-code-check")
    public ResponseEntity<Void> customerCodeDuplicateCheck(@RequestBody String customerCode){
        return service.customerCodeCheck(customerCode) == null ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseListCustomerDto> getCustomerList(@RequestBody String customerName,
                                                                   @RequestParam(defaultValue = "1") Integer page,
                                                                   @RequestParam(defaultValue = "8") Integer pageSize){
        ResponseListCustomerDto customer = service.getCustomerList(customerName, page, pageSize);
        List<ResponseCustomerDto> customerList = customer.getCustomerList();
        return customerList == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/{customerNo}")
    public ResponseEntity<ResponseCustomerDto> getCustomer(@PathVariable Long customerNo){
        ResponseCustomerDto customer = service.getCustomer(customerNo);
        return customer != null ?
                new ResponseEntity<>(customer, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{customerNo}/update")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long customerNo,
                                               @RequestBody UpdateCustomerDto dto){
        if (service.getCustomer(customerNo) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.updateCustomer(dto, customerNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{customerNo}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerNo){
        if (service.getCustomer(customerNo) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.removeCustomer(customerNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
