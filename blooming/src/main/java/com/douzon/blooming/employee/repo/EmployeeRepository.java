package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.response.EmployeeDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeRepository {

  @Select("SELECT employee_no FROM project.employee WHERE name = #{employeeName}")
  Long findEmployeeNoByName(String employeeName);

  @Select("SELECT employee_no, id, password, name, img, tel, email " +
      "FROM project.employee WHERE employee_no = #{employeeId}")
  Optional<ResponseEmployeeDto> findEmployeeByNo(Long employeeNo);

  @Select("<script>" +
      "SELECT COUNT(*) FROM project.employee" +
      "<where>" +
      "<if test='employeeNo != null'>" +
      "employee_no LIKE CONCAT('%', #{employeeNo}, '%')" +
      "</if>" +
      "<if test='name != null and name != \"\"'>" +
      "<choose>" +
      "<when test='employeeNo == null'>" +
      "name LIKE CONCAT('%', #{name}, '%')" +
      "</when>" +
      "<otherwise>" +
      "And name LIKE CONCAT('%', #{name}, '%')" +
      "</otherwise>" +
      "</choose>" +
      "</if>" +
      "<if test='role != null and role != \"\"'>" +
      "<choose>" +
      "<when test='employeeNo == null and name == null'>" +
      "role = #{role}" +
      "</when>" +
      "<otherwise>" +
      "And role = #{role}" +
      "</otherwise>" +
      "</choose>" +
      "</if>" +
      "</where>" +
      "</script>")
  Integer getCountEmployees(EmployeeSearchDto dto);

  @Select("<script>" +
      "SELECT employee_no as employeeNo, name, tel, email, role  FROM project.employee" +
      "<where>" +
      "<if test='dto.employeeNo != null'>" +
      "employee_no LIKE CONCAT('%', #{dto.employeeNo}, '%')" +
      "</if>" +
      "<if test='dto.name != null and dto.name != \"\"'>" +
      "<choose>" +
      "<when test='dto.employeeNo == null'>" +
      "name LIKE CONCAT('%', #{dto.name}, '%')" +
      "</when>" +
      "<otherwise>" +
      "And name LIKE CONCAT('%', #{dto.name}, '%')" +
      "</otherwise>" +
      "</choose>" +
      "</if>" +
      "<if test='dto.role != null and dto.role != \"\"'>" +
      "<choose>" +
      "<when test='dto.employeeNo == null and dto.name == null'>" +
      "role = #{dto.role}" +
      "</when>" +
      "<otherwise>" +
      "And role = #{dto.role}" +
      "</otherwise>" +
      "</choose>" +
      "</if>" +
      "</where>" +
      "LIMIT #{start}, #{pageSize}" +
      "</script>")
  @ResultType(ListEmployeeDto.class)
  List<ListEmployeeDto> findEmployeeListWithFilter(@Param("dto") EmployeeSearchDto dto, int start,
      int pageSize);

  @Update("UPDATE project.employee SET ${target} = #{newValue} WHERE employee_no = #{employeeNo}")
  void updateEmployeeByUpdateEmployeeDto(UpdateEmployeeDto updateEmployeeDto,
      @Param("employeeNo") Long employeeNo);

  @Delete("DELETE FROM project.employee WHERE employee_no = #{employeeNo}")
  void deleteEmployee(Long employeeNo);

  @Insert("INSERT INTO project.employee (id, password, name, img, role, tel, email) VALUE (#{id}, #{password}, #{name}, #{img}, #{role}, #{tel}, #{email})")
  void insertEmployee(InsertEmployeeDto insertEmployeeDto);

  @Select("SELECT employee_no, id, password, role FROM project.employee WHERE id = #{id}")
  Optional<EmployeeDto> findEmployeeById(String id);

  @Select("SELECT COUNT(*) FROM project.employee WHERE id = #{id}")
  boolean existById(String id);

  @Select("SELECT COUNT(*) FROM project.employee WHERE employee_no = #{employeeId}")
  boolean existByEmployeeNo(Long employeeNo);
}