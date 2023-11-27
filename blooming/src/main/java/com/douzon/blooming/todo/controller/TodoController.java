package com.douzon.blooming.todo.controller;

import com.douzon.blooming.todo.dto.reponse.ResponseTodoDto;
import com.douzon.blooming.todo.dto.request.RequestTodoDto;
import com.douzon.blooming.todo.service.TodoService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/{todoNo}")
  public ResponseEntity<ResponseTodoDto> getTodo(@PathVariable Long todoNo) {
    return ResponseEntity.ok(todoService.getTodo(todoNo));
  }

  @GetMapping
  public ResponseEntity<List<ResponseTodoDto>> getTodoList() {
    return ResponseEntity.ok(todoService.getTodoList());
  }

  @PostMapping
  public ResponseEntity<Void> addTodo(@RequestBody @Valid RequestTodoDto dto) {
    todoService.addTodo(dto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{todoNo}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long todoNo) {
    todoService.deleteTodo(todoNo);
    return ResponseEntity.noContent().build();
  }
}
