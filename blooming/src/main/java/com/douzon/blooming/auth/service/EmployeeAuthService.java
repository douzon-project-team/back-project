package com.douzon.blooming.auth.service;

import com.douzon.blooming.auth.dto.request.InsertEmployeeDto;
import com.douzon.blooming.auth.dto.request.LoginEmployeeDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.provider.TokenProvider;
import com.douzon.blooming.auth.repo.EmployeeAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeAuthService {

  private final AuthenticationManagerBuilder managerBuilder;
  private final EmployeeAuthRepository employeeAuthRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  public void signup(InsertEmployeeDto employeeDto) {
    employeeAuthRepository.insertEmployee(employeeDto.encodingPassword(passwordEncoder));
  }

  public TokenDto login(LoginEmployeeDto loginEmployeeDto) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        loginEmployeeDto.getId(), loginEmployeeDto.getPassword());
    Authentication authenticate = managerBuilder.getObject().authenticate(token);

    return tokenProvider.generateToken(authenticate);
  }
}
