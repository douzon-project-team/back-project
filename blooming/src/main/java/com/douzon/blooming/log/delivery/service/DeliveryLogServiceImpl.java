package com.douzon.blooming.log.delivery.service;

import com.douzon.blooming.log.delivery.dto.DeliveryLogDto;
import com.douzon.blooming.log.delivery.repo.DeliveryLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryLogServiceImpl implements
    DeliveryLogService {

  private final DeliveryLogRepository deliveryLogRepository;

  @Override
  public void addDeliveryLog(DeliveryLogDto deliveryLogDto) {
    deliveryLogRepository.insertDeliveryLogByDeliveryLogDto(deliveryLogDto);
  }
}
