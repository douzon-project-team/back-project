package com.douzon.blooming.instruction.dto;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestUpdateDto {
    private String instructionNo;
    private Long customerNo;
    private String instructionDate;
    private String expirationDate;
}
