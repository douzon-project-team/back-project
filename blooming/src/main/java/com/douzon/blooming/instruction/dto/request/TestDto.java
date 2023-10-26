package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProductListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class TestDto {
    private String employeeName;
    private String customerName;
    List<ProductListDto> products;
//    private String productCode;
//    private Integer amount;
    private String instructionDate;
    private String expirationDate;
    private Long progressStatus;
}
