package com.douzon.blooming.log.employee.repo;

import com.douzon.blooming.log.employee.dto.EmployeeLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeLogRepository {

  @Insert("INSERT INTO project.employee_log (ip_address, modifier_no, target_no, type) VALUE (#{ipAddress},#{modifierNo},#{employeeNo},#{type})")
  void insertEmployeeLogByEmployeeLogDto(EmployeeLogDto employeeLogDto);

}
