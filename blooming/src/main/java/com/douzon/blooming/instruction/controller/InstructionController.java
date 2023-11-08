package com.douzon.blooming.instruction.controller;

import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.InstructionSearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;
import com.douzon.blooming.instruction.service.InstructionService;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/instructions")
public class InstructionController {
    private final InstructionService service;

    @PostMapping
    public ResponseEntity<Void> addInstruction(@RequestBody RequestInstructionDto dto) {
        service.addInstruction(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<GetInstructionListDto> getInstructions(@ModelAttribute InstructionSearchDto dto){
        return ResponseEntity.ok().body(service.findInstructions(dto));
    }

    @GetMapping("/{instructionNo}")
    public ResponseEntity<GetInstructionDto> getInstruction(@PathVariable String instructionNo) {
        return ResponseEntity.ok().body(service.findInstruction(instructionNo));
    }

    @PutMapping("/{instructionNo}")
    public ResponseEntity<Void> updateInstruction(@RequestBody UpdateInstructionDto dto, @PathVariable String instructionNo) {
        service.updateInstruction(instructionNo, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{instructionNo}")
    public ResponseEntity<Void> removeInstruction(@PathVariable String instructionNo) {
        service.deleteInstruction(instructionNo);
        return ResponseEntity.noContent().build();
    }
}
