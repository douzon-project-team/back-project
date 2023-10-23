package com.douzon.blooming.productlog.dto;

import com.douzon.blooming.productlog.item.ProductLogType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLogDto {

  private String ipAddress;
  private Long modifierNo;
  private Long productNo;
  private ProductLogType type;
}
