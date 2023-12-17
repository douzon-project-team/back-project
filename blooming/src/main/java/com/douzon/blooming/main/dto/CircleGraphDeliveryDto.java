package com.douzon.blooming.main.dto;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CircleGraphDeliveryDto {

  private DeliveryStatus progress;
  private Long count;
}
