package com.douzon.blooming.product.dto;

import com.douzon.blooming.product.dto.response.ProductListDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ResponseProductListDto {

  private List<ProductListDto> productList;
  private Integer currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
}
