package com.douzon.blooming.log.instruction.service;

import com.douzon.blooming.log.LogService;
import com.douzon.blooming.log.instruction.dto.InstructionLogDto;
import com.douzon.blooming.log.instruction.repo.InstructionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructionLogService implements LogService<InstructionLogDto> {

  private final InstructionLogRepository instructionLogRepository;

  @Override
  public void addLog(InstructionLogDto instructionLogDto) {
    instructionLogRepository.insertInstructionLogByInstructionLogDto(instructionLogDto);
  }
}
