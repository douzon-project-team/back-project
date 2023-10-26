package com.douzon.blooming.instruction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ProductListDto {
    private String productCode;
    private Integer amount;
}
