package com.douzon.blooming.instruction.dto;

import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class TestRequestDto {
    private Long customerNo;

    List<ProductInstructionDto> products;

    private String instructionDate;

    private String expirationDate;

    private ProgressStatus progressStatus;
}
