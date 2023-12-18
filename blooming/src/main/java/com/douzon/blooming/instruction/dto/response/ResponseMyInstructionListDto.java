package com.douzon.blooming.instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseMyInstructionListDto {
    private List<ResponseMyInstructionDto> list;
}
