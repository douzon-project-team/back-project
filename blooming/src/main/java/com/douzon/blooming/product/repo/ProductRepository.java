package com.douzon.blooming.product.repo;

import com.douzon.blooming.product.dto.request.SearchProductDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductListDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductRepository {

  void insertByRequestProductDto(UpdateProductDto requestProductDto);

  void deleteByProductNo(long productNo);

  void updateByRequestProductDto(UpdateProductDto requestProductDto);

  Optional<ProductDto> findByProductNo(long productNo);

  List<ProductListDto> findAllBySearchProductDto(SearchProductDto searchProductDto);

  int getProductsCountBySearchProductDto(SearchProductDto searchProductDto);
}
