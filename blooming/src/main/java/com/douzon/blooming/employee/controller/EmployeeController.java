package com.douzon.blooming.employee.controller;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.dto.response.ResponseListEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginEmployeeDto loginEmployeeDto) {
    return ResponseEntity.ok().body(employeeService.login(loginEmployeeDto));
  }

  @GetMapping("/{employeeNo}")
  public ResponseEntity<ResponseEmployeeDto> getEmployeeByNo(@PathVariable Long employeeNo) {
    return ResponseEntity.ok(employeeService.getEmployeeByNo(employeeNo));
  }

  @GetMapping("/list")
  public ResponseEntity<ResponseListEmployeeDto> findEmployeeList(
      @RequestBody EmployeeSearchDto searchDto,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "8") Integer size) {
    return new ResponseEntity<>(employeeService.getEmployeeList(searchDto, page, size),
        HttpStatus.OK);
  }

  @PutMapping("/{employeeNo}")
  public ResponseEntity<Void> updateEmployee(@PathVariable Long employeeNo,
      @RequestBody UpdateEmployeeDto updateEmployeeDto) {
    if (checkAuthority(employeeNo)) {
      employeeService.updateEmployee(updateEmployeeDto, employeeNo);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.badRequest().build();
  }

  private boolean checkAuthority(Long employeeNo) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    EmployeeDetails details = (EmployeeDetails) authentication.getPrincipal();
    if (details.getEmployeeNo().equals(employeeNo)) {
      return true;
    }
    return false;
  }
}



