package com.douzon.blooming.instruction.dto;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestUpdateDto {
    private Long customerNo;
    private List<ProductInstructionDto> products;
    private String instructionDate;
    private String expirationDate;
}
