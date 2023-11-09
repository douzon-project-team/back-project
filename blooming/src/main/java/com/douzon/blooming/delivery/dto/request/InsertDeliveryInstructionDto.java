package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionProductDto;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InsertDeliveryInstructionDto {
    private String instructionNo;
    private String employeeName;
    private String customerName;
    @Setter
    private List<InsertDeliveryInstructionProductDto> products;
    private String instructionDate;
    private String expirationDate;
    private ProgressStatus progressStatus;
}