package com.douzon.blooming.product.service;

import com.douzon.blooming.product.dto.request.RequestProductDto;
import com.douzon.blooming.product.dto.response.ListProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.exception.NotFoundProductException;
import com.douzon.blooming.product.repo.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public void addProduct(RequestProductDto requestProductDto) {
    productRepository.insertByRequestProductDto(requestProductDto);
  }

  @Override
  public void removeProduct(long productNo) {
    productRepository.deleteByProductNo(productNo);
  }

  @Override
  public void updateProduct(RequestProductDto requestProductDto) {
    productRepository.updateByRequestProductDto(requestProductDto);
  }

  @Transactional(readOnly = true)
  @Override
  public ProductDto findProduct(String productNo) {
    return productRepository.findByProductCode(productNo)
        .orElseThrow(NotFoundProductException::new);
  }

  @Override
  public List<ListProductDto> findAllProductCode(String productCode) {
    return productRepository.findAllByProductCodeLike(productCode);
  }

  @Override
  public List<ListProductDto> findAllDesignation(String designation) {
    return productRepository.findAllByDesignationLike(designation);
  }
}
