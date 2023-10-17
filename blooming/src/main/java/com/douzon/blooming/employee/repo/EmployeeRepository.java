package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.EmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeRepository {

    @Select("SELECT password FROM employees WHERE id = #{id}")
    String login(LoginDto dto);

    @Insert("INSERT INTO ")
    void insertEmployee(EmployeeDto dto);

    void updateEmployee(EmployeeDto dto);
}