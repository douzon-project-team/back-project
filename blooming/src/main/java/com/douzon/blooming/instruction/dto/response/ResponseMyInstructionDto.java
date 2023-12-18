package com.douzon.blooming.instruction.dto.response;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ResponseMyInstructionDto {
    private String instructionNo;
    private LocalDate instructionDate;
    private LocalDate expirationDate;
    private ProgressStatus progressStatus;
}
