package com.douzon.blooming.delivery_instruction.dto.response;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryListInstructionDto  {
    private String deliveryNo;
    private String instructionNo;
    private String employeeName;
    private String customerName;
    private String instructionDate;
    private String expirationDate;
    private Integer productNo;
    private String productCode;
    private String productName;
    private Integer amount;
    private LocalDate deliveryDate;
    private DeliveryStatus deliveryStatus;
    @Setter
    private Integer instructionCount;
}