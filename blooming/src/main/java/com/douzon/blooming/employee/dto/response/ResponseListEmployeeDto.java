package com.douzon.blooming.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListEmployeeDto {
    private List<ListEmployeeDto> employeeList;
    private Integer currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
