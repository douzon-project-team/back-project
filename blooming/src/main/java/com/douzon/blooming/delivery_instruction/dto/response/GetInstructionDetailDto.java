package com.douzon.blooming.delivery_instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetInstructionDetailDto {
    private Long productNo;
    private String productCode;
    private String productName;
    private Integer amount;
}
