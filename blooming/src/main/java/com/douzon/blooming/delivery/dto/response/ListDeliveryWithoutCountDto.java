package com.douzon.blooming.delivery.dto.response;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ListDeliveryWithoutCountDto {
    private String deliveryNo;
    private String employeeName;
    private LocalDate deliveryDate;
    private DeliveryStatus deliveryStatus;
}
