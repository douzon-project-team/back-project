package com.douzon.blooming.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
public class RequestCustomerDto {

    @NotBlank
    @Size(min = 5, max = 5)
    @Pattern(regexp = "[A-Z]\\d{4}", message = "거래처 번호의 형식이 잘못되었습니다.")
    private String customerCode;

    @NotBlank
    @Size(min = 1, max = 10)
    private String customerName;

    @NotBlank
    @Size(min = 13, max =13)
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "연락처의 형식이 잘못되었습니다.")
    private String customerTel;
}
