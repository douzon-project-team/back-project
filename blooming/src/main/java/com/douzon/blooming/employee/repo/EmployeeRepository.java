package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeRepository {

    @Select("SELECT password FROM employees WHERE id = #{id}")
    String login(LoginDto dto);

    @Insert("INSERT INTO employee " +
            "VALUES (#{employeeId}, #{id}, #{password}, #{name}, #{img}, #{role}, #{tel}, #{eamil})")
    void insertEmployee(RequestEmployeeDto dto);

    @Select("SELECT id FROM employee WHERE id = #{id}")
    String idCheck(String id);

    @Select("SELECT employee_no FROM employee WHERE employee_no = #{employeeId}")
    Long employeeNoCheck(Long employeeNo);


    @Select("SELECT employee_no, id, password, name, img, tel, email " +
            "FROM employee WHERE employee_no = #{employeeId}")
    Optional<ResponseEmployeeDto> findEmployeeByNo(Long employeeNo);

    @Select("<script>" +
            "SELECT COUNT(*)" +
            "<where>" +
                "<if test='employeeNo != null'>" +
                    "employee_no LIKE CONCAT('%', #{dto.employeeNo}, '%')" +
                "</if>" +
                "<if test='name != null and name != \"\"'>" +
                    "<choose>" +
                        "<when test='employee == null'>" +
                            "name LIKE CONCAT('%', #{dto.name}, '%')" +
                        "</when>"+
                        "<otherwise>"+
                            "And name LIKE CONCAT('%', #{dto.name}, '%')" +
                        "</otherwise>"+
                    "</choose>"+
                "</if>" +
                "<if test='role != null and role != \"\"'>" +
                    "<choose>" +
                        "<when test='employee == null and name == null'>" +
                            "role = #{dto.role}" +
                        "</when>"+
                        "<otherwise>"+
                            "And role = #{dto.role}" +
                        "</otherwise>"+
                    "</choose>"+
                "</if>" +
            "</where>" +
            "</script>")
    Integer getCountEmployees(EmployeeSearchDto dto);

    @Select("<script>" +
            "SELECT employee_no as employeeNo, name, tel, email, role  FROM employee" +
            "<where>" +
                "<if test='employeeNo != null'>" +
                    "employee_no LIKE CONCAT('%', #{dto.employeeNo}, '%')" +
                "</if>" +
                "<if test='name != null and name != \"\"'>" +
                    "<choose>" +
                        "<when test='employee == null'>" +
                            "name LIKE CONCAT('%', #{dto.name}, '%')" +
                        "</when>"+
                        "<otherwise>"+
                             "And name LIKE CONCAT('%', #{dto.name}, '%')" +
                        "</otherwise>"+
                    "</choose>"+
                "</if>" +
                "<if test='role != null and role != \"\"'>" +
                    "<choose>" +
                        "<when test='employee == null and name == null'>" +
                            "role = #{dto.role}" +
                    "</when>"+
                        "<otherwise>"+
                            "And role = #{dto.role}" +
                        "</otherwise>"+
                    "</choose>"+
                "</if>" +
            "</where>" +
            "LIMIT #{start}, #{pageSize}" +
            "</script>")
    @ResultType(ListEmployeeDto.class)
    List<ListEmployeeDto> findEmployeeListWithFilter(EmployeeSearchDto dto, int page, int size);


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