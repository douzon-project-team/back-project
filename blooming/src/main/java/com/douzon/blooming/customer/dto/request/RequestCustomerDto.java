package com.douzon.blooming.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestCustomerDto {
    private String customerCode;
    private String customerName;
    private String customerTel;
}
