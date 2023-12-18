package com.douzon.blooming.delivery.dto.response;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListDeliveryDto {
    private String deliveryNo;
    private String employeeName;
    private LocalDate deliveryDate;
    private DeliveryStatus deliveryStatus;
    @Setter
    private Integer instructionCount;
}
