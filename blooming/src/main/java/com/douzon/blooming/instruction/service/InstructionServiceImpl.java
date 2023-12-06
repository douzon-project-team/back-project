package com.douzon.blooming.instruction.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseMyInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseMyInstructionListDto;
import com.douzon.blooming.instruction.exception.NotFoundInstructionException;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import com.douzon.blooming.product_instruction.repo.ProductInstructionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    dto.setEmployeeNo(employeeDetails.getEmployeeNo());
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
  public PageDto<ListInstructionDto> findInstructions(InstructionSearchDto searchDto) {
    int start = (searchDto.getPage()) * searchDto.getPageSize();
    List<ListInstructionDto> instructionList = instructionRepository.findInstructions(searchDto,
        start, searchDto.getPageSize());
    int count = instructionRepository.getCountInstructions(searchDto);

    return PageDto.<ListInstructionDto>builder()
        .list(instructionList)
        .currentPage(searchDto.getPage() + 1)
        .hasNextPage(start + searchDto.getPageSize() < count)
        .hasPreviousPage(start > 0)
        .build();
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

  @Override
  public ResponseMyInstructionListDto findMyInstruction() {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    return new ResponseMyInstructionListDto(instructionRepository.findMyInstruction(employeeDetails.getEmployeeNo()));
  }
}
