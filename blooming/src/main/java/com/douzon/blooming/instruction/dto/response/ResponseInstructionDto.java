package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProductListDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseInstructionDto {
    private String instructionNo;
    private String employeeName;
    private String customerName;
    @Setter
    private List<ProductListDto> productList;
    private LocalDate instructionDate;
    private LocalDate expirationDate;
    private Long progressStatus;
}
