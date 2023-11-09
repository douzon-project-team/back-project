package com.douzon.blooming.instruction.repo;


import com.douzon.blooming.instruction.dto.request.InsertInstructionDto;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface InstructionRepository {

    void insertInstruction(InsertInstructionDto insertDto);

    String getInstructionNo();

    Optional<GetInstructionDto> findInstruction(@Param("instructionNo")String instructionNo);

    List<ListInstructionDto> findInstructions(@Param("dto") InstructionSearchDto searchDto, Integer start, Integer pageSize);

    Integer getCountInstructions(@Param("dto") InstructionSearchDto searchDto);

    int updateInstruction(@Param("instructionNo") String instructionNo, @Param("dto") UpdateInstructionDto dto);

    int deleteInstruction(String instructionNo);
}
