package com.douzon.blooming.instruction.service;

import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionListDto;
import com.douzon.blooming.instruction.exception.NotFoundInstructionException;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import com.douzon.blooming.product_instruction.repo.ProductInstructionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructionServiceImpl implements InstructionService {

  private final InstructionRepository instructionRepository;
  private final ProductInstructionRepository productInstructionRepository;

  @Override
  public String addInstruction(RequestInstructionDto dto) {
    instructionRepository.insertInstruction(dto);
    return instructionRepository.getInstructionNo();
  }

  @Override
  public ResponseInstructionDto findInstruction(String instructionNo) {
    ResponseInstructionDto responseInstructionDto = instructionRepository.findInstruction(
            instructionNo)
        .orElseThrow(NotFoundInstructionException::new);
    List<ResponseProductInstructionDto> list = productInstructionRepository.findProductInstructionByInstructionNo(
        responseInstructionDto.getInstructionNo());
    responseInstructionDto.setProducts(list);
    return responseInstructionDto;
  }

  @Override
  public ResponseInstructionListDto findInstructions(InstructionSearchDto searchDto) {
    int start = (searchDto.getPage()) * searchDto.getPageSize();
    List<ListInstructionDto> instructionList = instructionRepository.findInstructions(searchDto,
        start, searchDto.getPageSize());
    int searchInstructionCount = instructionRepository.getCountInstructions(searchDto);

    boolean hasNextPage = (start + searchDto.getPageSize()) < searchInstructionCount;
    boolean hasPreviousPage = start > 0;

    return new ResponseInstructionListDto(instructionList, searchDto.getPage(), hasNextPage,
        hasPreviousPage);
  }

  @Override
  public void updateInstruction(UpdateInstructionDto dto, String instructionNo) {
    dto.setInstructionNo(instructionNo);
    if (instructionRepository.updateInstruction(dto) < 0) {
      throw new NotFoundInstructionException();
    }
  }

  @Override
  public void deleteInstruction(String instructionNo) {
    if (instructionRepository.deleteInstruction(instructionNo) < 0) {
      throw new NotFoundInstructionException();
    }
  }
}
