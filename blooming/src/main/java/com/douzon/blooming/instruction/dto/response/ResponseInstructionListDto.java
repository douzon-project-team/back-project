package com.douzon.blooming.instruction.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInstructionListDto {
    private List<ListInstructionDto> instructions;
    private Integer currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
