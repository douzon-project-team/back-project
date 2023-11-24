package com.douzon.blooming.todo.exception;

public class NotFoundTodoException extends RuntimeException {

  public NotFoundTodoException() {
    super("Not found todo");
  }
}
