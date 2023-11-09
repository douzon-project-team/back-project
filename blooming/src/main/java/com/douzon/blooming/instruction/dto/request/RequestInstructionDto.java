package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class RequestInstructionDto {


    @NotNull(message = "거래처가 존재하지 않습니다.")
    private Long customerNo;

    List<ProductInstructionDto> products;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate instructionDate;

    @NotBlank
    private LocalDate expirationDate;

    @Max(2)
    private ProgressStatus progressStatus;
}

