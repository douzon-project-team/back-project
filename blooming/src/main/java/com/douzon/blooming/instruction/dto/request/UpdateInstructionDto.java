package com.douzon.blooming.instruction.dto.request;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateInstructionDto {
  @Setter
  private String instructionNo;
  private Long customerNo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate instructionDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate expirationDate;
}
