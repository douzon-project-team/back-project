package com.douzon.blooming.product_instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ProductInstructionDto {

    @NotNull
    private Long productNo;

    @NotNull
    @Min(value = 1)
    private Integer amount;

    private String status;
}
