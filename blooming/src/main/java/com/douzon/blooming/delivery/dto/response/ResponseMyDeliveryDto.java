package com.douzon.blooming.delivery.dto.response;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMyDeliveryDto {
    private String deliveryNo;
    private LocalDate deliveryDate;
    private DeliveryStatus progressStatus;
}
