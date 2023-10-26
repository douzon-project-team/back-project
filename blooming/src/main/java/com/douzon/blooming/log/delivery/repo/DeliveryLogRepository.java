package com.douzon.blooming.log.delivery.repo;

import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryLogRepository {

  @Insert("INSERT INTO project.instruction_log (ip_address, modifier_no, instruction_no, type) VALUE (#{idAddress},#{modifierNo},#{deliveryNo},#{type}})")
  void insertDeliveryLogByDeliveryLogDto(DeliveryLogDto deliveryLogDto);
}
