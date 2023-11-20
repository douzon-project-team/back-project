package com.douzon.blooming.product_instruction.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateProductInstructionDto {

  private Long productNo;

  @NotNull
  @Min(value = 1)
  private Long amount;

  private String instructionNo;

  public void setProductNoAndInstructionNo(Long productNo, String instructionNo) {
    this.productNo = productNo;
    this.instructionNo = instructionNo;
  }
}
