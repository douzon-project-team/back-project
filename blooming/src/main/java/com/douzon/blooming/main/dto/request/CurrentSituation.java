package com.douzon.blooming.main.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentSituation implements Serializable {

  private Long thisMonthCount;
  private Long allCount;
}