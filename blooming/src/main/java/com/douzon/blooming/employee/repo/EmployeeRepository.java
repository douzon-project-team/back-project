package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeRepository {

    @Select("SELECT password FROM employees WHERE id = #{id}")
    String login(LoginDto dto);

    @Insert("INSERT INTO employee " +
            "VALUES (#{employeeId}, #{id}, #{password}, #{name}, #{img}, #{role}, #{tel}, #{eamil})")
    void insertEmployee(RequestEmployeeDto dto);

    @Select("SELECT id FROM employee WHERE id = #{id}")
    String idCheck(String id);

    @Select("SELECT employee_no as employeeNo FROM employee WHERE employee_no = #{employeeId}")
    Long employeeNoCheck(Long employeeNo);

    @Select("SELECT employee_no as employeeNo, id, password, name, img, role, tel, email" +
            "FROM employee WHERE employee_no = #{employeeId}")
    ResponseEmployeeDto getEmployeeByNo(Long employeeNo);

//    @Update("UPDATE employee SET")
    void updateEmployee(RequestEmployeeDto dto);

    @Delete("DELETE FROM employee WHERE employee_no = #{employeeNo}")
    void deleteEmployee(Long employeeNo);

}