package com.douzon.blooming.product.dto.request;

import javax.validation.constraints.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InsertProductDto {

  @NotBlank(message = "상품 코드는 필수 값입니다.")
  @Pattern(regexp = "^[a-zA-Z]{2}\\d{4}$")
  private String productCode;
  @NotBlank(message = "명칭은 필수 값입니다.")
  @Size(max = 45)
  private String productName;
  @NotBlank(message = "규격은 필수 값입니다.")
  private String standard;
  @NotNull(message = "개수는 필수 값입니다.")
  @Max(Integer.MAX_VALUE)
  private int unit;
  @NotNull(message = "무게는 필수 값입니다.")
  @Max(Integer.MAX_VALUE)
  private int weight;
  @NotNull(message = "가격은 필수 값입니다.")
  @Max(Integer.MAX_VALUE)
  private int price;

}
