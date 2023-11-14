package com.douzon.blooming.delivery_instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteDeliveryInstructionProductDto {
    private String instructionNo;
    private Long productNo;
}
