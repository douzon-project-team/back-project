package com.douzon.blooming.auth.controller;

import com.douzon.blooming.auth.dto.request.InsertEmployeeDto;
import com.douzon.blooming.auth.dto.request.LoginEmployeeDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.service.EmployeeAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeAuthController {

  private final EmployeeAuthService employeeAuthService;

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginEmployeeDto loginEmployeeDto) {
    System.out.println(loginEmployeeDto);
    return ResponseEntity.ok().body(employeeAuthService.login(loginEmployeeDto));
  }

  @PostMapping("/auth/register")
  public ResponseEntity<Void> register(@RequestBody InsertEmployeeDto insertEmployeeDto) {
    employeeAuthService.signup(insertEmployeeDto);
    return ResponseEntity.noContent().build();
  }
}
