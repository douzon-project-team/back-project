package com.douzon.blooming.instruction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestAddDto {

  private Long employeeNo;
  private Long customerNo;
  private String instructionDate;
  private String expirationDate;
  private ProgressStatus progressStatus;
}
