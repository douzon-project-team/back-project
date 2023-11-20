package com.douzon.blooming.instruction.dto.request;

import com.douzon.blooming.SearchDto;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class InstructionSearchDto extends SearchDto {

  private final String employeeName;
  private ProgressStatus progressStatus;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  public InstructionSearchDto(ProgressStatus progressStatus, String employeeName,
      LocalDate startDate, LocalDate endDate, Integer page, Integer pageSize) {
    super(pageSize, page);
    this.progressStatus = progressStatus;
    this.endDate = endDate;
    this.startDate = startDate;
    this.employeeName = employeeName == null ? DEFAULT_STRING : employeeName;
  }
}
