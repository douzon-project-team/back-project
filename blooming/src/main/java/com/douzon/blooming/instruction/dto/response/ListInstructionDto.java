package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ListInstructionDto {
    private String instructionNo;
    private String employeeName;
    private String customerName;
    private String instructionDate;
    private String expirationDate;
    private Integer productNo;
    private String productCode;
    private String productName;
    private Integer amount;
}
