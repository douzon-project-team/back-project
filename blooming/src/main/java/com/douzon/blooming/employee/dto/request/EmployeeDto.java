package com.douzon.blooming.employee.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class EmployeeDto {
    private Integer employeeNo;
    private String id;
    private String password;
    private String name;
    private File img;
    private String tel;
    private String email;
}
