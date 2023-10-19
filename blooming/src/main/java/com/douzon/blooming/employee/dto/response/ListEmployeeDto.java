package com.douzon.blooming.employee.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListEmployeeDto {
    private Long employeeNo;
    private String name;
    private Long role;
    private String tel;
    private String email;
}
