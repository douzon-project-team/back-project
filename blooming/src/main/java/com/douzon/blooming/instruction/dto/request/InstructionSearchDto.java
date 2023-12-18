package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.SearchDto;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class InstructionSearchDto extends SearchDto {
  private final String instructionNo;
  private final String employeeName;
  private final ProgressStatus progressStatus;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate startDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate endDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate expirationStartDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate expirationEndDate;

  public InstructionSearchDto(String instructionNo, ProgressStatus progressStatus, String employeeName,
      LocalDate startDate, LocalDate endDate, LocalDate expirationStartDate, LocalDate expirationEndDate, Integer page, Integer pageSize) {
    super(pageSize, page);
    this.instructionNo = instructionNo == null ? DEFAULT_STRING : instructionNo;
    this.progressStatus = progressStatus;
    this.endDate = endDate;
    this.startDate = startDate;
    this.expirationStartDate = expirationStartDate;
    this.expirationEndDate = expirationEndDate;
    this.employeeName = employeeName == null ? DEFAULT_STRING : employeeName;
  }
}
