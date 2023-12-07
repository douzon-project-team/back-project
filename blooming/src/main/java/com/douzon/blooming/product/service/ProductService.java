package com.douzon.blooming.product.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.product.dto.request.InsertProductDto;
import com.douzon.blooming.product.dto.request.ProductCodeCheckDto;
import com.douzon.blooming.product.dto.request.ProductSearchDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.dto.response.ProductListDto;

;

public interface ProductService {

  void addProduct(InsertProductDto insertProductDto);

  void removeProduct(long productNo);

  void updateProduct(UpdateProductDto requestProductDto);

  ProductDto findProduct(long productNo);

  PageDto<ProductListDto> findProducts(ProductSearchDto productSearchDto);

  boolean ProductCodeCheck(String productCode);
}
