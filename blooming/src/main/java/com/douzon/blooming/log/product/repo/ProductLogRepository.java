package com.douzon.blooming.log.product.repo;

import com.douzon.blooming.log.product.dto.ProductLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductLogRepository {

  @Insert("INSERT INTO project.product_log (ip_address, modifier_no, product_no, type) VALUE (#{idAddress}, #{modifierNo}, #{productNo}, #{type})")
  void insertProductLogByProductLogDto(ProductLogDto productLogDto);
}
