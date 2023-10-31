package com.douzon.blooming.instruction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetInstructionListDto {
    private List<ListInstructionDto> instructions;
    private Integer currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
