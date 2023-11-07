package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
public class InsertInstructionDto {
    private Long employeeNo;
    private Long customerNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate instructionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private ProgressStatus progressStatus;
}
