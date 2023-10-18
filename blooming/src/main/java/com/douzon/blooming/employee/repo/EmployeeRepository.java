package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select("SELECT employee_no, id, password, name, img, role, tel, email" +
            "FROM employee WHERE employee_no = #{employeeId}")
    @ResultType(ResponseEmployeeDto.class)
    ResponseEmployeeDto getEmployeeByNo(Long employeeNo);

    @Select("<script>" +
            "SELECT employee_no as employeeNo, name, tel, email, role  FROM employee" +
            "<where>" +
            "<if test='employeeNo != null'>" +
            "employee_no LIKE CONCAT('%', #{employeeNo}, '%')" +
            "</if>" +
            "<if test='name != null and name != \"\"'>" +
            " AND name LIKE CONCAT('%', #{name}, '%')" +
            "</if>" +
            "<if test='role != null and role != \"\"'>" +
            " AND role = #{role}" +
            "</if>" +
            "</where>" +
            "</script>")
    @ResultType(ResponseListEmployeeDto.class)
    List<ResponseListEmployeeDto> getEmployeeListWithFilter(EmployeeSearchDto dto);


    @Update("UPDATE employee SET id = #{id} WHERE employee_no = #{employeeNo}")
    void updateId(Long employeeNo, String id);

    @Update("UPDATE employee SET name = #{name} WHERE employee_no = #{employeeNo}")
    void updateName(Long employeeNo, String name);

    @Update("UPDATE employee SET password = #{password} WHERE employee_no = #{employeeNo}")
    void updatePassword(Long employeeNo, String password);

    @Update("UPDATE employee SET img = #{img} WHERE employee_no = #{employeeNo}")
    void updateImg(Long employeeNo, String img);

    @Update("UPDATE employee SET tel = #{tel} WHERE employee_no = #{employeeNo}")
    void updateTel(Long employeeNo, String tel);

    @Update("UPDATE employee SET email = #{email} WHERE employee_no = #{employeeNo}")
    void updateEmail(Long employeeNo, String email);

    @Delete("DELETE FROM employee WHERE employee_no = #{employeeNo}")
    void deleteEmployee(Long employeeNo);

}