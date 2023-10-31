package com.douzon.blooming.instruction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchDto {
    private Long progressStatus;
    private String employeeName;
    private String startDate;
    private String endDate;
    private Integer page = 1;
    private Integer pageSize = 8;
}
