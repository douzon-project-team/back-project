package com.douzon.blooming.product.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
  @NotBlank(message = "상품 코드는 필수 값입니다.")
  @Pattern(regexp = "^[a-zA-Z]{2}\\d{4}$")
  private String productCode;
  @NotBlank(message = "명칭은 필수 값입니다.")
  private String designation;
  @NotBlank(message = "규격은 필수 값입니다.")
  private String standard;
  @NotNull(message = "개수는 필수 값입니다.")
  private int unit;
}
