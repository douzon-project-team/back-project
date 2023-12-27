package com.douzon.blooming.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllEmployeeListDto {
    private List<EmployeeListDto> list;
}
