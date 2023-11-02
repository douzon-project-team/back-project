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
public class SearchDto {
    private ProgressStatus progressStatus;
    private String employeeName;
    private String startDate;
    private String endDate;
    private Integer page = 1;
    private Integer pageSize = 8;
}
