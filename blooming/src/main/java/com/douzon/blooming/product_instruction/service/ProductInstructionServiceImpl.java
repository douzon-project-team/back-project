package com.douzon.blooming.product_instruction.service;

import com.douzon.blooming.product_instruction.dto.request.AddProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.DeleteProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.UpdateProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductRemainAmountDto;
import com.douzon.blooming.product_instruction.exception.NotFoundProductInstructionException;
import com.douzon.blooming.product_instruction.repo.ProductInstructionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductInstructionServiceImpl implements ProductInstructionService {

  private final ProductInstructionRepository productInstructionRepository;

  @Override
  public void addProductInstruction(AddProductInstructionDto dto, String instructionNo) {
    dto.setInstructionNo(instructionNo);
    productInstructionRepository.insertProductInstruction(dto);
  }

  @Override
  public void updateProductInstruction(UpdateProductInstructionDto dto, Long productNo,
      String instructionNo) {
    dto.setProductNoAndInstructionNo(productNo,instructionNo);
    if (productInstructionRepository.updateProductInstructionByDto(dto) < 0) {
      throw new NotFoundProductInstructionException();
    }
  }

  @Override
  public void deleteProductInstruction(Long productNo, String instructionNo) {
    DeleteProductInstructionDto dto = new DeleteProductInstructionDto(
        productNo, instructionNo);
    if (productInstructionRepository.deleteProductInstructionByDto(dto) < 0) {
      throw new NotFoundProductInstructionException();
    }
  }

  @Override
  public ResponseProductRemainAmountDto getRemainAmount(String instructionNo, Integer productNo) {
    return productInstructionRepository.getProductRemainAmount(instructionNo, productNo);
  }
}
