package com.douzon.blooming.instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class ResponseInstructionListDto {
    private List<ResponseInstructionDto> instructionList;
    private Integer currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
