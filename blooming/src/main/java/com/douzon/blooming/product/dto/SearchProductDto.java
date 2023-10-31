package com.douzon.blooming.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SearchProductDto {

  private static final Integer DEFAULT_SIZE = 10;
  private static final Integer DEFAULT_PAGE = 0;

  private String productCode;
  private String designation;
  private Integer size = DEFAULT_SIZE;
  private Integer page = DEFAULT_PAGE;

  public String toQuery() {
    StringBuilder sb = new StringBuilder();
    sb.append("WHERE");
    if (productCode != null) {
      sb.append(" product_code LIKE %").append(productCode).append("% AND");
    }
    if (designation != null) {
      sb.append(" designation LIKE %").append(designation).append("%");
    } else {
      sb.delete(0, sb.length() - 4);
    }
    sb.append(" LIMIT ").append(size).append(" OFFSET ").append(page * size);
    return sb.toString();
  }
}
