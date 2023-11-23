package com.douzon.blooming.product_instruction.controller;

import com.douzon.blooming.product_instruction.dto.request.AddProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.UpdateProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductRemainAmountDto;
import com.douzon.blooming.product_instruction.service.ProductInstructionService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-instruction")
public class ProductInstructionController {

  private final ProductInstructionService productInstructionService;

  @GetMapping("/{instructionNo}/{productNo}")
  public ResponseEntity<ResponseProductRemainAmountDto> getRemainAmount(@PathVariable String instructionNo,
                                                                        @PathVariable Integer productNo){
    return ResponseEntity.ok().body(productInstructionService.getRemainAmount(instructionNo, productNo));
  }

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
