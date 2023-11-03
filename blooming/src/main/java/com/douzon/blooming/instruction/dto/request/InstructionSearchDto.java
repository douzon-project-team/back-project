package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstructionSearchDto {
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 8;

    private ProgressStatus progressStatus;
    private String employeeName;
    private String startDate;
    private String endDate;
    private Integer page = DEFAULT_PAGE;
    private Integer pageSize = DEFAULT_PAGE_SIZE;
}
