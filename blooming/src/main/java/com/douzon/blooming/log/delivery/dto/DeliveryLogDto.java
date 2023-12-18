package com.douzon.blooming.log.delivery.dto;

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
public class DeliveryLogDto {

  private String ipAddress;
  private Long modifierNo;
  private String deliveryNo;
  private LogType type;
}
