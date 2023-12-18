package com.douzon.blooming.instruction.repo;


import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseMyInstructionDto;
import com.douzon.blooming.main.dto.BarGraphDto;
import com.douzon.blooming.main.dto.CircleGraphInstructionDto;
import com.douzon.blooming.main.dto.ExpirationDateNearInstruction;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InstructionRepository {

  void insertInstruction(RequestInstructionDto insertDto);

  String getInstructionNo();

  Optional<ResponseInstructionDto> findInstruction(@Param("instructionNo") String instructionNo);

  List<ListInstructionDto> findInstructions(@Param("dto") InstructionSearchDto searchDto,
      Integer start, Integer pageSize);

  Integer getCountInstructions(@Param("dto") InstructionSearchDto searchDto);

  int updateInstruction(@Param("dto") UpdateInstructionDto dto);

  int deleteInstruction(String instructionNo);

  Long findInstructionCount();

  Long findThisMonthInstructionCount();

  List<ExpirationDateNearInstruction> findExpirationDateNearInstruction();

  List<BarGraphDto> findMainPageBarGraphData(@Param("type") String type);

  List<CircleGraphInstructionDto> findMainPageCircleGraphData(@Param("type") String type);

  List<ResponseMyInstructionDto> findMyInstruction(Long employeeNo);
}
