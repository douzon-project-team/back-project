package com.douzon.blooming;

import com.douzon.blooming.auth.exception.NotFoundEmployeeAuthException;
import com.douzon.blooming.auth.exception.TokenMissingAuthorizationInfoException;
import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
import com.douzon.blooming.employee.exception.EmployeeNotFoundException;
import com.douzon.blooming.employee.exception.UnsupportedTargetTypeException;
import com.douzon.blooming.log.exception.UnsupportedLogTypeException;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectAdvice {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> serverError(Exception e) {
    e.printStackTrace();
    return ResponseEntity.internalServerError().body(e.getMessage());
  }

  @ExceptionHandler({
      NotFoundEmployeeAuthException.class,
      TokenMissingAuthorizationInfoException.class,
      UnsupportedEmployeeRoleException.class,
      EmployeeNotFoundException.class,
      UnsupportedTargetTypeException.class,
      UnsupportedLogTypeException.class,
      IllegalArgumentException.class
  })
  public ResponseEntity<String> requestError(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(BindingException.class)
  public ResponseEntity<String> requestError(BindingException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
