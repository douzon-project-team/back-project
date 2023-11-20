package com.douzon.blooming.product_instruction.controller;

import com.douzon.blooming.product_instruction.dto.request.AddProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.UpdateProductInstructionDto;
import com.douzon.blooming.product_instruction.service.ProductInstructionService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-instruction")
public class ProductInstructionController {

  private final ProductInstructionService productInstructionService;

  @PostMapping("/instruction/{instructionNo}")
  public ResponseEntity<Void> addProductInstruction(@PathVariable String instructionNo,
      @RequestBody @Valid
          AddProductInstructionDto dto) {
    productInstructionService.addProductInstruction(dto, instructionNo);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/instruction/{instructionNo}/productNo/{productNo}")
  public ResponseEntity<Void> updateProductInstruction(@PathVariable String instructionNo,
      @PathVariable Long productNo,
      @RequestBody @Valid UpdateProductInstructionDto dto) {
    productInstructionService.updateProductInstruction(dto, productNo, instructionNo);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/instruction/{instructionNo}/productNo/{productNo}")
  public ResponseEntity<Void> deleteProductInstruction(@PathVariable String instructionNo,
      @PathVariable Long productNo) {
    productInstructionService.deleteProductInstruction(productNo, instructionNo);
    return ResponseEntity.noContent().build();
  }
}
