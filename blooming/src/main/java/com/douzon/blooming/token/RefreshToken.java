package com.douzon.blooming.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@Getter
public class RefreshToken {

  @Id
  private String token;

  private Long employeeNo;
}