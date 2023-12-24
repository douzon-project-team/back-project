package com.douzon.blooming;

import com.douzon.blooming.auth.exception.EmployeePermissionDefinedException;
import com.douzon.blooming.auth.exception.TokenMissingAuthorizationInfoException;
import com.douzon.blooming.auth.exception.UnsupportedEmployeeRoleException;
import com.douzon.blooming.customer.exception.NotFoundCustomerException;
import com.douzon.blooming.delivery.exception.DeliveryException;
import com.douzon.blooming.delivery_instruction.exception.RemainAmountException;
import com.douzon.blooming.employee.exception.NotFoundEmployeeException;
import com.douzon.blooming.employee.exception.ImageUploadException;
import com.douzon.blooming.employee.exception.NotFoundImageException;
import com.douzon.blooming.employee.exception.PasswordDoesNotMatchException;
import com.douzon.blooming.employee.exception.UnsupportedTargetTypeException;
import com.douzon.blooming.instruction.exception.DeadLockException;
import com.douzon.blooming.instruction.exception.InstructionException;
import com.douzon.blooming.log.exception.UnsupportedLogTypeException;
import com.douzon.blooming.product.exception.NotFoundProductException;
import com.douzon.blooming.product_instruction.exception.NotFoundProductInstructionException;
import com.douzon.blooming.product_instruction.exception.UnsupportedProductStatusException;
import com.douzon.blooming.token.exception.NotFoundRefreshTokenException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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
      EmployeePermissionDefinedException.class
  })
  public ResponseEntity<String> forbiddenError(Exception e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
  }

  @ExceptionHandler({
      NotFoundEmployeeException.class,
      NotFoundCustomerException.class,
      DeliveryException.class,
      NotFoundImageException.class,
      NotFoundProductException.class,
      InstructionException.class,
      NotFoundProductInstructionException.class,
  })
  public ResponseEntity<String> notFoundError(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler({
      TokenMissingAuthorizationInfoException.class,
      UnsupportedEmployeeRoleException.class,
      UnsupportedTargetTypeException.class,
      UnsupportedLogTypeException.class,
      IllegalArgumentException.class,
      PasswordDoesNotMatchException.class,
      UnsupportedProductStatusException.class,
      ImageUploadException.class,
      NotFoundRefreshTokenException.class,
      DeadLockException.class,
      RemainAmountException.class
  })
  public ResponseEntity<String> requestError(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler({
      BindException.class
  })
  public ResponseEntity<String> requestError(BindException e) {
    return ResponseEntity.badRequest().body(e.getFieldErrors().toString());
  }
}
