package com.douzon.blooming.main.dto;

import com.douzon.blooming.instruction.dto.ProgressStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CircleGraphDto {
  private ProgressStatus progress;
  private Long count;
}
