package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetInstructionDto {
    private String instructionNo;
    private String employeeName;
    private String customerName;
    @Setter
    private List<ResponseProductInstructionDto> products;
    private String instructionDate;
    private String expirationDate;
    private ProgressStatus progressStatus;
}
