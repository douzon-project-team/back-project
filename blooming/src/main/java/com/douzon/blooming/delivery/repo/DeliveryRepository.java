package com.douzon.blooming.delivery.repo;

import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveriesDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ListDeliveryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeliveryRepository {
    void insertDelivery(Long employeeNo, @Param("dto") InsertDeliveryDto dto);

    String getDeliveryNo();

    int deleteDelivery(String deliveryNo);

    List<ListDeliveryDto> findDeliveries(@Param("dto") DeliverySearchDto dto, Integer start, Integer pageSize);

    int getCountDeliveries(@Param("dto") DeliverySearchDto searchDto);

    Optional<GetDeliveryDto> findDelivery(String deliveryNo);
}
