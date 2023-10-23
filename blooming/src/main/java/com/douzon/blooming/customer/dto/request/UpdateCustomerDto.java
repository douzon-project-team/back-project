package com.douzon.blooming.customer.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCustomerDto {
    private String customerName;
    private String customerTel;
}
