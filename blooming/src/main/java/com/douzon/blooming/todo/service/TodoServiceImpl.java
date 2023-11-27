package com.douzon.blooming.todo.service;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.todo.dto.reponse.ResponseTodoDto;
import com.douzon.blooming.todo.dto.request.RequestTodoDto;
import com.douzon.blooming.todo.exception.NotFoundTodoException;
import com.douzon.blooming.todo.repo.TodoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  public void addTodo(RequestTodoDto dto) {
    todoRepository.insertTodo(dto, getEmployeeNo());
  }

  @Override
  public void deleteTodo(Long todoNo) {
    if (todoRepository.deleteTodoByNo(todoNo, getEmployeeNo()) <= 0) {
      throw new NotFoundTodoException();
    }
  }

  @Override
  public List<ResponseTodoDto> getTodoList() {
    return todoRepository.findAllByEmployeeNo(getEmployeeNo());
  }

  @Override
  public ResponseTodoDto getTodo(Long todoNo) {
    return todoRepository.findByTodoNoAndEmployeeNo(todoNo, getEmployeeNo())
        .orElseThrow(NotFoundTodoException::new);
  }

  private Long getEmployeeNo() {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return employeeDetails.getEmployeeNo();
  }
}
