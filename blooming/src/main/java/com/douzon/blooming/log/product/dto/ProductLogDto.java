package com.douzon.blooming.log.product.dto;

import com.douzon.blooming.log.LogType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductLogDto {

  private String ipAddress;
  private Long modifierNo;
  private Long productNo;
  private LogType type;
}
