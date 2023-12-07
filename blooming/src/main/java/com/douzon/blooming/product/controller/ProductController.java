package com.douzon.blooming.product.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.product.dto.request.InsertProductDto;
import com.douzon.blooming.product.dto.request.ProductCodeCheckDto;
import com.douzon.blooming.product.dto.request.ProductSearchDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
import com.douzon.blooming.product.dto.response.ProductDto;
import com.douzon.blooming.product.dto.response.ProductListDto;
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

  @GetMapping("/code/{productCode}")
  public ResponseEntity<?> duplicateCheckCustomerCode(@PathVariable String productCode){
    return ResponseEntity.ok().body(!productService.ProductCodeCheck(productCode));
  }

  @GetMapping("/{productNo}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable Long productNo) {
    return ResponseEntity.ok(productService.findProduct(productNo));
  }

  @GetMapping("/list")
  public ResponseEntity<PageDto<ProductListDto>> getProducts(
      @ModelAttribute ProductSearchDto productSearchDto) {
    return ResponseEntity.ok(productService.findProducts(productSearchDto));
  }

  @PostMapping
  public ResponseEntity<Void> addProduct(@RequestBody @Valid InsertProductDto insertProductDto) {
    productService.addProduct(insertProductDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{productNo}")
  public ResponseEntity<Void> removeProduct(@PathVariable Long productNo) {
    productService.removeProduct(productNo);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{productNo}")
  public ResponseEntity<Void> updateProduct(@PathVariable Long productNo,
      @RequestBody @Valid UpdateProductDto requestProductDto) {
    requestProductDto.setProductNo(productNo);
    productService.updateProduct(requestProductDto);
    return ResponseEntity.noContent().build();
  }
}
