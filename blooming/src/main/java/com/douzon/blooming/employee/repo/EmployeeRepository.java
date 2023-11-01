package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.EmployeeDto;
import com.douzon.blooming.employee.dto.response.ListEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeRepository {

  Long findEmployeeNoByName(String employeeName);

  Optional<ResponseEmployeeDto> findEmployeeByNo(Long employeeNo);

  Integer getCountEmployees(EmployeeSearchDto dto);

  List<ListEmployeeDto> findEmployeeListWithFilter(@Param("employeeSearchDto") EmployeeSearchDto employeeSearchDto,
      @Param("start") int start,
      @Param("pageSize") int pageSize);

  void updateEmployeeByUpdateEmployeeDto(UpdateEmployeeDto updateEmployeeDto,
      @Param("employeeNo") Long employeeNo);

  void deleteEmployee(Long employeeNo);

  void insertEmployee(InsertEmployeeDto insertEmployeeDto);

  Optional<EmployeeDto> findEmployeeById(String id);

  boolean existById(String id);

  boolean existByEmployeeNo(Long employeeNo);
}