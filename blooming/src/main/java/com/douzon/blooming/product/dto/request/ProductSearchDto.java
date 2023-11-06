package com.douzon.blooming.product.dto.request;

import com.douzon.blooming.SearchDto;
import lombok.Getter;

@Getter
public class ProductSearchDto extends SearchDto {

  private final String productCode;
  private final String productName;

  protected ProductSearchDto() {
    super();
    this.productCode = DEFAULT_STRING;
    this.productName = DEFAULT_STRING;
  }

  public ProductSearchDto(String productCode, String productName, Integer pageSize, Integer page) {
    super(pageSize, page);
    this.productCode = productCode == null ? DEFAULT_STRING : productCode;
    this.productName = productName == null ? DEFAULT_STRING : productName;
  }
}
