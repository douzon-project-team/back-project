package com.douzon.blooming.product.controller;

import com.douzon.blooming.product.dto.ResponseProductListDto;
import com.douzon.blooming.product.dto.request.SearchProductDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.service.ProductService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

  @GetMapping("/{productNo}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable Long productNo) {
    return ResponseEntity.ok(productService.findProduct(productNo));
  }

  @GetMapping("/list")
  public ResponseEntity<ResponseProductListDto> getProducts(
      @ModelAttribute SearchProductDto searchProductDto) {
    return ResponseEntity.ok(productService.findProducts(searchProductDto));
  }

  @PostMapping
  public ResponseEntity<Void> addProduct(@RequestBody @Valid UpdateProductDto requestProductDto) {
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
      @RequestBody UpdateProductDto requestProductDto) {
    requestProductDto.setProductNo(productNo);
    productService.updateProduct(requestProductDto);
    return ResponseEntity.noContent().build();
  }
}
