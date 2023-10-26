package com.douzon.blooming.instruction.service;

import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionListDto;

public interface InstructionService {
    void addInstruction(RequestInstructionDto dto);
    ResponseInstructionDto getInstruction(String instructionNo);
    ResponseInstructionListDto getInstructionList(SearchDto searchDto);
    void updateInstruction(String instructionNo, RequestInstructionDto dto);
    RequestInstructionDto removeInstruction(String instructionNo);
}
