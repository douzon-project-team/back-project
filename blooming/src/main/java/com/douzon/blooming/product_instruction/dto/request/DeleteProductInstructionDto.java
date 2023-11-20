package com.douzon.blooming.product_instruction.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DeleteProductInstructionDto {

  @NotBlank
  private Long productNo;

  private String instructionNo;
}
