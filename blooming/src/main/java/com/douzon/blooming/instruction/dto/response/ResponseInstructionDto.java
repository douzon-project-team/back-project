package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInstructionDto {
    private String instructionNo;
    private String employeeName;
    private String customerNo;
    private String customerName;
    private String instructionDate;
    private String expirationDate;
    private ProgressStatus progressStatus;


    @Setter
    private List<ResponseProductInstructionDto> products;
}
