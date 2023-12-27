package com.douzon.blooming.employee.repo;

import com.douzon.blooming.employee.dto.request.AuthUpdateEmployeeDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.*;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeRepository {

  Long findEmployeeNoByName(String employeeName);

  Optional<ResponseEmployeeDto> findEmployeeByNo(Long employeeNo);

  int getEmployeesCountBySearchEmployeeDto(EmployeeSearchDto dto);

  List<EmployeeListDto> findAllByEmployeeSearchDto(
      @Param("employeeSearchDto") EmployeeSearchDto employeeSearchDto, @Param("start") int start);

  void updateEmployeeByUpdateEmployeeDto(@Param("dto") UpdateEmployeeDto updateEmployeeDto,
      @Param("employeeNo") Long employeeNo);

  void updateEmployeeByAuthUpdateEmployeeDto(
      @Param("dto") AuthUpdateEmployeeDto authUpdateEmployeeDto,
      @Param("employeeNo") Long employeeNo);

  void deleteEmployee(Long employeeNo);

  void insertEmployee(InsertEmployeeDto insertEmployeeDto);

  Optional<EmployeeDto> findEmployeeById(String id);

  boolean existById(String id);

  boolean existByEmployeeNo(Long employeeNo);

  FindEmployeeDto findEmployeeNoByDto(@Param("id") String id);

  void updateEmployeeImage(@Param("imageUrl") String imageUrl,
      @Param("employeeNo") Long employeeNo);

  String findEmployeeImageByEmployeeNo(@Param("employeeNo") Long employeeNo);

  void addImageByImageNameAndEmployeeNo(@Param("imageName") String imageName,@Param("employeeNo") Long employeeNo);

  List<EmployeeListDto> findAllEmployeeList();
}