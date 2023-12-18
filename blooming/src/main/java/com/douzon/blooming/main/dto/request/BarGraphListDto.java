package com.douzon.blooming.main.dto.request;

import com.douzon.blooming.main.dto.BarGraphDto;
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
public class BarGraphListDto {

  List<BarGraphDto> instructionData;
  List<BarGraphDto> deliveryData;
}
