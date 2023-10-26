package com.douzon.blooming.instruction.conotroller;

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


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/instructions")
public class InstructionController {
    private final InstructionService service;

    @PostMapping("/insert")
    public ResponseEntity<Void> addInstruction(@RequestBody(required = false) RequestInstructionDto dto) {
        service.addInstruction(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseInstructionListDto> getInstructionList(@RequestParam Long progressStatus,
                                                                         @RequestParam String employeeName,
                                                                         @RequestParam String startDate,
                                                                         @RequestParam String endDate,
                                                                         @RequestParam(defaultValue = "1") Integer page,
                                                                         @RequestParam(defaultValue = "8") Integer pageSize) {
        SearchDto searchDto = new SearchDto(progressStatus, employeeName, startDate, endDate, page, pageSize);
        log.error(searchDto.toString());
        ResponseInstructionListDto listDto = service.getInstructionList(searchDto);
        log.error(listDto.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{instructionNo}")
    public ResponseEntity<ResponseInstructionDto> getInstruction(@PathVariable String instructionNo) {
        return new ResponseEntity<>(service.getInstruction(instructionNo), HttpStatus.OK);
    }

    @PutMapping("/{instructionNo}")
    public ResponseEntity<Void> updateInstruction(@RequestBody(required = false) RequestInstructionDto dto,
                                                  @PathVariable String instructionNo) {
        service.updateInstruction(instructionNo, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{instructionNo}")
    public ResponseEntity<Void> removeInstruction(@PathVariable String instructionNo) {
        service.removeInstruction(instructionNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
