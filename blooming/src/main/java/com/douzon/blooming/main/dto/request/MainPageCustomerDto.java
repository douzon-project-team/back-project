package com.douzon.blooming.main.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageCustomerDto implements Serializable {

  private Long customerNo;
  private String customerName;
  private Long count;
}
