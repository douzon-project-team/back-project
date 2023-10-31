package com.douzon.blooming.instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ListInstructionDto {
    private String instructionNo;
    private String employeeName;
    private Long customerNo;
    private String customerName;
    private String instructionDate;
    private String expirationDate;
    private Integer progressStatus;
}
