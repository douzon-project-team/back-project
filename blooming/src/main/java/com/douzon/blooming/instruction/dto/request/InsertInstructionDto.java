package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class InsertInstructionDto {
    private Long employeeNo;
    private Long customerNo;
    private String instructionDate;
    private String expirationDate;
    private ProgressStatus progressStatus;
}
