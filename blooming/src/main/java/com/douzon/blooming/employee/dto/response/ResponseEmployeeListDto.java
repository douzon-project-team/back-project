package com.douzon.blooming.employee.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ResponseEmployeeListDto {

  private List<EmployeeListDto> employeeList;
  private Integer currentPage;
  private boolean hasNextPage;
  private boolean hasPreviousPage;
}
