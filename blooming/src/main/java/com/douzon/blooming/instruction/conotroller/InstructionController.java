package com.douzon.blooming.instruction.conotroller;

import com.douzon.blooming.customer.service.CustomerService;
import com.douzon.blooming.instruction.dto.InsertDto;
import com.douzon.blooming.instruction.dto.ProductListDto;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionDto;
import com.douzon.blooming.instruction.dto.response.ResponseInstructionListDto;
import com.douzon.blooming.instruction.service.InstructionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/instruction")
public class InstructionController {
    private final InstructionService service;

    @PostMapping("/insert")
    public ResponseEntity<Void> insertInstruction(@RequestBody(required = false) RequestInstructionDto dto) {
        log.error(dto.toString());
        service.addInstruction(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{instructionNo}")
    public ResponseEntity<ResponseInstructionDto> getInstruction(@PathVariable String instructionNo) {
        return new ResponseEntity<>(service.getInstruction(instructionNo), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Void> listInstruction(SearchDto searchDto) {
        service.getInstructionList(searchDto);
        return null;
    }

    @PutMapping("/{instructionNo}")
    public ResponseEntity<Void> updateInstruction(@RequestBody(required = false) RequestInstructionDto dto,
                                                  @PathVariable String instructionNo) {
        service.updateInstruction(instructionNo, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{instructionNo}")
    public ResponseEntity<Void> deleteInstruction(@PathVariable String instructionNo) {
        service.removeInstruction(instructionNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
