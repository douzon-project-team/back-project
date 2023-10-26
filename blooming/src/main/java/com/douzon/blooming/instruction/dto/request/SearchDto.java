package com.douzon.blooming.instruction.dto.request;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;


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
