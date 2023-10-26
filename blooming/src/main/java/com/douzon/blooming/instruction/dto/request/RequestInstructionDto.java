package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProductListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class RequestInstructionDto {
    private String employeeName;
    private String customerName;
    private List<ProductListDto> products;
    private LocalDate instructionDate;
    private LocalDate expirationDate;
    private Long progressStatus;
}
