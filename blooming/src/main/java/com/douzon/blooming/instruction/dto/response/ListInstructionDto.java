package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ListInstructionDto {
    private String instructionNo;
    private String employeeName;
    private Long customerNo;
    private String customerName;
    private String instructionDate;
    private String expirationDate;
    private ProgressStatus progressStatus;
}
