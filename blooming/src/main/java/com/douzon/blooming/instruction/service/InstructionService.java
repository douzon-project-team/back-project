package com.douzon.blooming.instruction.service;

import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;

public interface InstructionService {
    void addInstruction(RequestInstructionDto dto);

    GetInstructionDto findInstruction(String instructionNo);

    GetInstructionListDto findInstructions(InstructionSearchDto instructionSearchDto);

    void updateInstruction(String instructionNo, UpdateInstructionDto dto);

    void deleteInstruction(String instructionNo);
}
