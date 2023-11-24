package com.douzon.blooming.todo.dto.reponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseTodoDto {

  private Long todoNo;
  private String content;
  private boolean checked;
}
