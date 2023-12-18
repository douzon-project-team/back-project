package com.douzon.blooming.todo.exception;

public class NotFoundTodoException extends RuntimeException {

  public NotFoundTodoException() {
    super("해당 투두를 찾을 수 없습니다.");
  }
}
