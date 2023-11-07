package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class UpdateInstructionDto {
    private Long customerNo;
    private List<ProductInstructionDto> products;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate instructionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
