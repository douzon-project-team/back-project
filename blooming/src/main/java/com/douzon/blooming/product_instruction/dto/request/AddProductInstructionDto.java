package com.douzon.blooming.product_instruction.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddProductInstructionDto {

  @NotNull
  private Long productNo;

  @NotNull
  @Min(value = 1)
  private Integer amount;

  @Setter
  private String instructionNo;
}
