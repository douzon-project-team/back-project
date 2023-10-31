package com.douzon.blooming.instruction.repo;


import com.douzon.blooming.instruction.dto.request.InsertInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
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

    Optional<GetInstructionDto> findByInstructionNo(String instructionNo);

    List<ListInstructionDto> findInstructionList(@Param("dto") SearchDto searchDto, Integer start, Integer pageSize);

    Integer getCountInstructions(@Param("dto") SearchDto searchDto);

    void updateInstruction(@Param("instructionNo") String instructionNo, @Param("dto") UpdateInstructionDto dto);

    void deleteInstruction(String instructionNo);
}
