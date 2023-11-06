package com.douzon.blooming.product.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.product.dto.request.InsertProductDto;
import com.douzon.blooming.product.dto.request.ProductSearchDto;
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
  public void addProduct(InsertProductDto insertProductDto) {
    productRepository.insertByRequestProductDto(insertProductDto);
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
  public PageDto<ProductListDto> findProducts(ProductSearchDto dto) {
    int start = dto.getPage() * dto.getPage();

    List<ProductListDto> productList = productRepository.findAllBySearchProductDto(dto, start);

    int count = productRepository.getProductsCountBySearchProductDto(dto);

    return PageDto.<ProductListDto>builder()
        .list(productList)
        .currentPage(dto.getPage() + 1)
        .hasNextPage(start + dto.getPageSize() < count)
        .hasPreviousPage(start > 0)
        .build();
  }
}
