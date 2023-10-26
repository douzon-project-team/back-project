package com.douzon.blooming.instruction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class InsertDto {
    private Long employeeNo;
    private Long customerNo;
    private List<ProductListDto> productList;
    private LocalDate instructionDate;
    private LocalDate expirationDate;
    private Long progressStatus;
}
