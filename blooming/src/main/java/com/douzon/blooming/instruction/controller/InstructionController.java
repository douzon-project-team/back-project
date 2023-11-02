package com.douzon.blooming.instruction.controller;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;
import com.douzon.blooming.instruction.service.InstructionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/instructions")
public class InstructionController {
    private final InstructionService service;

    @PostMapping("/insert")
    public ResponseEntity<Void> addInstruction(@RequestBody(required = false) RequestInstructionDto dto){
        service.addInstruction(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<GetInstructionListDto> getInstructionList(@RequestParam(required = false) ProgressStatus progressStatus,
                                                                    @RequestParam(required = false) String employeeName,
                                                                    @RequestParam(required = false) String startDate,
                                                                    @RequestParam(required = false) String endDate,
                                                                    @RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "8") Integer pageSize) {
        SearchDto searchDto = new SearchDto(progressStatus, employeeName, startDate, endDate, page, pageSize);
        return new ResponseEntity<>(service.getInstructionList(searchDto), HttpStatus.OK);
    }

    @GetMapping("/{instructionNo}")
    public ResponseEntity<GetInstructionDto> getInstruction(@PathVariable String instructionNo){
        return new ResponseEntity<>(service.getInstruction(instructionNo), HttpStatus.OK);
    }

    @PutMapping("/{instructionNo}")
    public ResponseEntity<Void> modifyInstruction(@RequestBody(required = false) UpdateInstructionDto dto,
                                                  @PathVariable String instructionNo) {
        service.updateInstruction(instructionNo, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{instructionNo}")
    public ResponseEntity<Void> deleteInstruction(@PathVariable String instructionNo){
        service.deleteInstruction(instructionNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
