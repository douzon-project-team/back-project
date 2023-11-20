package com.douzon.blooming.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class ResponseCustomerDto {
    private Long customerNo;
    private String customerCode;
    private String customerName;
    private String customerTel;
    private String ceo;
    private String sector;
}
