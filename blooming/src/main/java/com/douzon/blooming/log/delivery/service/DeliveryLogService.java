package com.douzon.blooming.log.delivery.service;

import com.douzon.blooming.log.LogService;
import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import com.douzon.blooming.log.delivery.repo.DeliveryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryLogService implements LogService<DeliveryLogDto> {

  private final DeliveryLogRepository deliveryLogRepository;

  @Override
  public void addLog(DeliveryLogDto deliveryLogDto) {
    deliveryLogRepository.insertDeliveryLogByDeliveryLogDto(deliveryLogDto);
  }
}
