package com.douzon.blooming.delivery_instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertDeliveryInstructionProductDto {
    private Long productNo;
    private Integer amount;
}
