package com.douzon.blooming.delivery_instruction.dto.request;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateInstructionProductDto {
    private String instructionNo;
    private List<ProductInstructionDto> products;
}
