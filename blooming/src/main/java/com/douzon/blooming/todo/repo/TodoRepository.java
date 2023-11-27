package com.douzon.blooming.todo.repo;

import com.douzon.blooming.todo.dto.reponse.ResponseTodoDto;
import com.douzon.blooming.todo.dto.request.RequestTodoDto;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TodoRepository {

  @Insert("INSERT INTO project.todo (employee_no, content) "
        + "VALUE (#{employeeNo}, #{dto.content}) ")
  void insertTodo(@Param("dto") RequestTodoDto dto, Long employeeNo);

  @Delete("DELETE FROM project.todo "
        + "WHERE todo_no = #{todoNo} AND employee_no = #{employeeNo}")
  int deleteTodoByNo(@Param("todoNo") Long todoNo, @Param("employeeNo") Long employeeNo);

  @Select("SELECT todo_no, content "
        + "FROM project.todo "
        + "WHERE employee_no = #{employeeNo}")
  List<ResponseTodoDto> findAllByEmployeeNo(Long employeeNo);

  @Select("SELECT todo_no, content, checked "
        + "FROM project.todo "
        + "WHERE employee_no = #{employeeNo} AND todo_no = #{todoNo}")
  Optional<ResponseTodoDto> findByTodoNoAndEmployeeNo(@Param("todoNo") Long todoNo, @Param("employeeNo") Long employeeNo);
}
