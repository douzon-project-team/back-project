package com.douzon.blooming.auth.repo;

import com.douzon.blooming.auth.dto.request.InsertEmployeeDto;
import com.douzon.blooming.auth.dto.response.EmployeeDto;
import java.util.Optional;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeAuthRepository {

  @Insert("INSERT INTO project.employee (id, password, name, img, role, tel, email) VALUE (#{id}, #{password}, #{name}, #{img}, #{role}, #{tel}, #{email})")
  void insertEmployee(InsertEmployeeDto insertEmployeeDto);

  @Select("SELECT id, password, role FROM project.employee WHERE id = #{id}")
  Optional<EmployeeDto> findEmployeeById(String id);
}
