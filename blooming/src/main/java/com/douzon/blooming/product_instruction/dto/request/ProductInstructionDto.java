package com.douzon.blooming.product_instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ProductInstructionDto {

    @NotNull(message = "품목이 존재하지 않습니다.")
    private Long productNo;

    @NotNull(message = "수량이 없습니다.")
    @Min(value = 1, message = "최소 1개의 수량이 필요합니다.")
    private Integer amount;

    private String status;
}
