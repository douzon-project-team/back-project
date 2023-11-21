package com.douzon.blooming.instruction.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseAddInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.service.InstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/instructions")
public class InstructionController {

  private final InstructionService instructionService;

  @GetMapping("/list")
  public ResponseEntity<PageDto<ListInstructionDto>> getInstructions(
      @ModelAttribute InstructionSearchDto dto) {
    return ResponseEntity.ok().body(instructionService.findInstructions(dto));
  }

  @GetMapping("/{instructionNo}")
  public ResponseEntity<ResponseInstructionDto> getInstruction(@PathVariable String instructionNo) {
    ResponseInstructionDto instruction = instructionService.findInstruction(instructionNo);
    return ResponseEntity.ok().body(instruction);
  }

  @PostMapping
  public ResponseEntity<ResponseAddInstructionDto> addInstruction(
      @RequestBody RequestInstructionDto dto) {
    return ResponseEntity.ok(new ResponseAddInstructionDto(instructionService.addInstruction(dto)));
  }

  @PutMapping("/{instructionNo}")
  public ResponseEntity<Void> updateInstruction(@RequestBody UpdateInstructionDto dto,
      @PathVariable String instructionNo) {
    instructionService.updateInstruction(dto, instructionNo);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{instructionNo}")
  public ResponseEntity<Void> removeInstruction(@PathVariable String instructionNo) {
    instructionService.deleteInstruction(instructionNo);
    return ResponseEntity.noContent().build();
  }
}
