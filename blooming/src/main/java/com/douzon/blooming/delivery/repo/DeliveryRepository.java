package com.douzon.blooming.delivery.repo;

import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.RequestDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ListDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ListDeliveryWithoutCountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeliveryRepository {
    void insertDelivery(Long employeeNo, @Param("dto") RequestDeliveryDto dto);

    String getDeliveryNo();

    int deleteDelivery(String deliveryNo);

    List<ListDeliveryDto> findDeliveries(@Param("dto") DeliverySearchDto dto, Integer start, Integer pageSize);

    int getCountDeliveries(@Param("dto") DeliverySearchDto searchDto);

    Optional<GetDeliveryDto> findDelivery(String deliveryNo);

    int updateDelivery(String deliveryNo, @Param("dto") UpdateDeliveryDto dto);
}
