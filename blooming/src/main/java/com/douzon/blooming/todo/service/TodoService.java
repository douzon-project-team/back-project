package com.douzon.blooming.todo.service;

import com.douzon.blooming.todo.dto.reponse.ResponseTodoDto;
import com.douzon.blooming.todo.dto.request.RequestTodoDto;
import java.util.List;

public interface TodoService {

  void addTodo(RequestTodoDto dto);

  void deleteTodo(Long todoNo);

  List<ResponseTodoDto> getTodoList();

  ResponseTodoDto getTodo(Long todoNo);

  void updateTodoCheck(Long todoNo);
}
