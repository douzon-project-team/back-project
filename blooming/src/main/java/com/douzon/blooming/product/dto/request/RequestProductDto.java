package com.douzon.blooming.product.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RequestProductDto {

  @Setter
  private Long productNo;
  private String productCode;
  private String designation;
  private String standard;
  private int unit;
}
