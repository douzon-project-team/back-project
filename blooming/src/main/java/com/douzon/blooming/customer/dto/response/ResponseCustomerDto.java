package com.douzon.blooming.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCustomerDto {
    private Long customerNo;
    private String customerCode;
    private String customerName;
    private String customerTel;
}
