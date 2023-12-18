package com.douzon.blooming.log.product.service;

import com.douzon.blooming.log.LogService;
import com.douzon.blooming.log.product.dto.ProductLogDto;
import com.douzon.blooming.log.product.repo.ProductLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductLogService implements LogService<ProductLogDto> {

  private final ProductLogRepository productLogRepository;

  @Override
  public void addLog(ProductLogDto productLogDto) {
    productLogRepository.insertProductLogByProductLogDto(productLogDto);
  }
}
