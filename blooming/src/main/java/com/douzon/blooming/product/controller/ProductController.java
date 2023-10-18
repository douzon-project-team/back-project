package com.douzon.blooming.product.controller;

import com.douzon.blooming.product.dto.request.RequestProductDto;
import com.douzon.blooming.product.dto.response.ListProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/{productCode}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable String productCode) {
    return ResponseEntity.ok(productService.findProduct(productCode));
  }

  @GetMapping("/like/product-code/{productCode}")
  public ResponseEntity<List<ListProductDto>> getProductsByProductCode(
      @PathVariable String productCode) {
    return ResponseEntity.ok(productService.findAllProductCode(productCode));
  }

  @GetMapping("/like/designation/{designation}")
  public ResponseEntity<List<ListProductDto>> getProductsByDesignation(
      @PathVariable String designation) {
    return ResponseEntity.ok(productService.findAllDesignation(designation));
  }

  @PostMapping
  public ResponseEntity<Void> addProduct(@RequestBody RequestProductDto requestProductDto) {
    productService.addProduct(requestProductDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{productNo}")
  public ResponseEntity<Void> removeProduct(@PathVariable Long productNo) {
    productService.removeProduct(productNo);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{productNo}")
  public ResponseEntity<Void> updateProduct(@PathVariable Long productNo,
      @RequestBody RequestProductDto requestProductDto) {
    requestProductDto.setProductNo(productNo);
    productService.updateProduct(requestProductDto);
    return ResponseEntity.noContent().build();
  }
}
