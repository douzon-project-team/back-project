package com.douzon.blooming.delivery_instruction.service;

import com.douzon.blooming.delivery_instruction.dto.request.DeleteDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateDeliveryInstructionProductDto;

public interface DeliveryInstructionService {
    void addDeliveryInstructions(String deliveryNo, InsertDeliveryInstructionDto dto);

    void updateDeliveryInstructions(String deliveryNo, UpdateDeliveryInstructionProductDto dto);

    void deleteDeliveryInstructions(String deliveryNo, DeleteDeliveryInstructionProductDto dto);
}
