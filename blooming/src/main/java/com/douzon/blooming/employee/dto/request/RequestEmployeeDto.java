package com.douzon.blooming.employee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEmployeeDto {
    private Long employeeNo;
    private String id;
    private String password;
    private String name;
    private Byte[] img;
    private Long role;
    private String tel;
    private String email;
}
