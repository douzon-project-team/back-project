package com.douzon.blooming.productlog.service;

import com.douzon.blooming.productlog.dto.ProductLogDto;
import com.douzon.blooming.productlog.repo.ProductLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductLogServiceImpl implements ProductLogService {

  private final ProductLogRepository productLogRepository;

  @Override
  public void addProductLog(ProductLogDto productLogDto) {
    productLogRepository.insertProductLogByInsertProductDto(productLogDto);
  }
}
