package com.douzon.blooming.product_instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductInstructionDto {
    private Long productNo;
    private Integer amount;
    private String status;
}
