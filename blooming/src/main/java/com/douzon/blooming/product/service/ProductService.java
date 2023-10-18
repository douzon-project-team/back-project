package com.douzon.blooming.product.service;

import com.douzon.blooming.product.dto.request.RequestProductDto;
import com.douzon.blooming.product.dto.response.ListProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import java.util.List;

public interface ProductService {

  void addProduct(RequestProductDto requestProductDto);

  void removeProduct(long productNo);

  void updateProduct(RequestProductDto requestProductDto);

  ProductDto findProduct(String productNo);

  List<ListProductDto> findAllProductCode(String productCode);

  List<ListProductDto> findAllDesignation(String designation);
}
