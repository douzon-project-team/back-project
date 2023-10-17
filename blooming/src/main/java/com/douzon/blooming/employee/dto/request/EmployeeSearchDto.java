package com.douzon.blooming.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchDto {
    private Long employeeNo;
    private String name;
    private Long role;
}
