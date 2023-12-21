package com.douzon.blooming.instruction.dto.request;

import java.time.LocalDate;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
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
  @Max(12)
  private String instructionNo;
  @NotBlank(message = "사원 번호는 필수입니다.")
  private Long customerNo;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate instructionDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate expirationDate;
}
