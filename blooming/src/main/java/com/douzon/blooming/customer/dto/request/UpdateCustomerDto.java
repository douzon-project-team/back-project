package com.douzon.blooming.customer.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCustomerDto {
    @NotBlank
    @Size(min = 1, max = 10)
    private String customerName;

    @NotBlank
    @Size(min = 13, max =13)
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "연락처의 형식이 잘못되었습니다.")
    private String customerTel;

    @NotBlank
    @Size(min = 1, max = 10)
    private String ceo;

}

