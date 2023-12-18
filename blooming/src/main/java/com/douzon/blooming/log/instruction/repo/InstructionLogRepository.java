package com.douzon.blooming.log.instruction.repo;

import com.douzon.blooming.log.instruction.dto.InstructionLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InstructionLogRepository {

  @Insert("INSERT INTO project.instruction_log (ip_address, modifier_no, instruction_no, type) VALUE (#{ipAddress},#{modifierNo},#{instructionNo},#{type})")
  void insertInstructionLogByInstructionLogDto(InstructionLogDto insertInstructionLogDto);
}
