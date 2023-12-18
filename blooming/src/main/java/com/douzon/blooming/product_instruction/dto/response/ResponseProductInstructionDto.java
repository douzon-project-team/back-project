package com.douzon.blooming.product_instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseProductInstructionDto {
    private Long productNo;
    private String productCode;
    private String productName;
    private Integer amount;
    private Integer remainAmount;
}
