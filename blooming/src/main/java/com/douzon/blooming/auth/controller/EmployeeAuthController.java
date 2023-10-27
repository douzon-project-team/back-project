package com.douzon.blooming.auth.controller;

import com.douzon.blooming.auth.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmployeeAuthController {

  private final EmployeeService employeeService;

  @PostMapping("/register")
  public ResponseEntity<Void> register(@RequestBody InsertEmployeeDto insertEmployeeDto) {
    employeeService.signup(insertEmployeeDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/employees/{employeeNo}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeNo) {
    employeeService.removeEmployee(employeeNo);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/employees/no/check")
  public ResponseEntity<Void> noCheck(@RequestBody Long employeeNo) {
    return ResponseEntity
        .status(
            employeeService.employeeNoCheck(employeeNo) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
        .build();
  }

  @GetMapping("/employees/id/check")
  public ResponseEntity<Void> idCheck(@RequestBody String id) {
    return ResponseEntity
        .status(
            employeeService.idCheck(id) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
        .build();
  }
}
