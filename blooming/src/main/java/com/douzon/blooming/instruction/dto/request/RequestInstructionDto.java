package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RequestInstructionDto {
    private Long employeeNo;
    private Long customerNo;
    List<ProductInstructionDto> products;
    private String instructionDate;
    private String expirationDate;
    private Integer progressStatus;
}
