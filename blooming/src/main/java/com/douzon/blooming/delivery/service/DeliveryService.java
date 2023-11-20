package com.douzon.blooming.delivery.service;


import com.douzon.blooming.PageDto;
import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.RequestDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveriesDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ListDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ResponseDeliveryDto;
import com.douzon.blooming.delivery_instruction.dto.response.DeliveryListInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;

import java.util.List;

public interface DeliveryService {
    ResponseDeliveryDto addDelivery(RequestDeliveryDto dto);

    GetDeliveryDto findDelivery(String deliveryNo);

    PageDto<DeliveryListInstructionDto> findDeliveries(DeliverySearchDto dto);

    void updateDelivery(String deliveryNo, UpdateDeliveryDto dto);

    void removeDelivery(String deliveryNo);
}

