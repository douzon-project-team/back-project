package com.douzon.blooming.product.service;

import com.douzon.blooming.product.dto.ResponseProductListDto;
import com.douzon.blooming.product.dto.request.SearchProductDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.dto.response.ProductListDto;
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
  public void addProduct(UpdateProductDto requestProductDto) {
    productRepository.insertByRequestProductDto(requestProductDto);
  }

  @Override
  public void removeProduct(long productNo) {
    productRepository.deleteByProductNo(productNo);
  }

  @Override
  public void updateProduct(UpdateProductDto requestProductDto) {
    productRepository.updateByRequestProductDto(requestProductDto);
  }

  @Transactional(readOnly = true)
  @Override
  public ProductDto findProduct(long productNo) {
    return productRepository.findByProductNo(productNo)
        .orElseThrow(NotFoundProductException::new);
  }

  @Transactional(readOnly = true)
  @Override
  public ResponseProductListDto findProducts(SearchProductDto dto) {
    if (dto.getPage() != 0) {
      dto.setPage(dto.getPage() - 1);
    }

    List<ProductListDto> productList = productRepository.findAllBySearchProductDto(dto);

    int count = productRepository.getProductsCountBySearchProductDto(dto);
    int start = dto.getPage() * dto.getSize();

    return ResponseProductListDto.builder()
        .productList(productList)
        .currentPage(dto.getPage() + 1)
        .hasNextPage(start + dto.getSize() < count)
        .hasPreviousPage(start > 0)
        .build();

  }
}
