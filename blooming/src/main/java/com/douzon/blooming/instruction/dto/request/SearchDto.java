package com.douzon.blooming.instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SearchDto {
    private Long progressStatus;
    private String employeeName;
    private String startDate;
    private String endDate;
    private Integer page;
    private Integer pageSize;
}
