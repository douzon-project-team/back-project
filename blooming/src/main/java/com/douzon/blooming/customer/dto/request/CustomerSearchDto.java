package com.douzon.blooming.customer.dto.request;

import com.douzon.blooming.SearchDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomerSearchDto extends SearchDto {

    private final String customerCode;
    private final String customerName;
    private final String sector;

    public CustomerSearchDto(Integer pageSize, Integer page, String customerCode, String customerName, String sector) {
        super(10, page);
        this.customerCode = customerCode == null ? DEFAULT_STRING : customerCode;
        this.customerName = customerName == null ? DEFAULT_STRING : customerName;
        this.sector = sector == null ? DEFAULT_STRING : sector;
    }
}
