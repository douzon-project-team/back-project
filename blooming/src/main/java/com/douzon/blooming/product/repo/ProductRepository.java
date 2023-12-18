package com.douzon.blooming.product.repo;

import com.douzon.blooming.product.dto.request.InsertProductDto;
import com.douzon.blooming.product.dto.request.ProductCodeCheckDto;
import com.douzon.blooming.product.dto.request.ProductSearchDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.dto.response.ProductListDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductRepository {

  void insertByRequestProductDto(InsertProductDto insertProductDto);

  int updateProductHide(long productNo);

  int updateByRequestProductDto(UpdateProductDto requestProductDto);

  Optional<ProductDto> findByProductNo(long productNo);

  List<ProductListDto> findAllBySearchProductDto(
      @Param("productSearchDto") ProductSearchDto productSearchDto, @Param("start") int start);

  int getProductsCountBySearchProductDto(@Param("productSearchDto") ProductSearchDto productSearchDto);

  boolean existByproductCode(String productCode);
}
