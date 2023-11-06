package com.douzon.blooming.product.service;

import com.douzon.blooming.product.dto.ResponseProductListDto;
import com.douzon.blooming.product.dto.request.SearchProductDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;

public interface ProductService {

  void addProduct(UpdateProductDto requestProductDto);

  void removeProduct(long productNo);

  void updateProduct(UpdateProductDto requestProductDto);

  ProductDto findProduct(long productNo);

  ResponseProductListDto findProducts(SearchProductDto searchProductDto);
}
