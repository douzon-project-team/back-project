package com.douzon.blooming.instruction.repo;


import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface InstructionRepository {

    void insertInstruction(RequestInstructionDto insertDto);

    String getInstructionNo();

    Optional<ResponseInstructionDto> findInstruction(@Param("instructionNo") String instructionNo);

    List<ListInstructionDto> findInstructions(@Param("dto") InstructionSearchDto searchDto, Integer start, Integer pageSize);

    Integer getCountInstructions(@Param("dto") InstructionSearchDto searchDto);

    int updateInstruction(@Param("dto") UpdateInstructionDto dto);

    int deleteInstruction(String instructionNo);
}
