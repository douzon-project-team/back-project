package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsertDeliveryDto {
    private List<GetInstructionDto> instructions;
    private DeliveryStatus progressStatus;
    private String deliveryDate;
}
