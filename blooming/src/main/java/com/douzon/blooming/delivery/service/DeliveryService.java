package com.douzon.blooming.delivery.service;


import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveriesDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;

import java.util.List;

public interface DeliveryService {
    void addDelivery(InsertDeliveryDto dto);

    GetDeliveryDto findDelivery(String deliveryNo);

    List<GetInstructionDetailDto> findDeliveryDetail(String deliveryNo, String instructionNo);

    GetDeliveriesDto findDeliveries(DeliverySearchDto dto);

    void updateDelivery(String deliveryNo, UpdateDeliveryDto dto);

    void removeDelivery(String deliveryNo);
}

