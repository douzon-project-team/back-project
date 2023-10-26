package com.douzon.blooming.auth.service;

import com.douzon.blooming.auth.dto.response.EmployeeDto;
import com.douzon.blooming.auth.exception.NotFoundEmployeeAuthException;
import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.auth.repo.EmployeeAuthRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final EmployeeAuthRepository employeeAuthRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return employeeAuthRepository.findEmployeeById(username)
        .map(this::createEmployeeDetails)
        .orElseThrow(NotFoundEmployeeAuthException::new);
  }

  private EmployeeDetails createEmployeeDetails(EmployeeDto employeeDto) {
    return new EmployeeDetails(
        employeeDto.getEmployeeNo(),
        employeeDto.getId(),
        employeeDto.getPassword(),
        employeeDto.getRole());
  }

  private UserDetails createUserDetails(EmployeeDto employeeDto) {
    return new User(
        employeeDto.getId(),employeeDto.getPassword(),
        Collections.singleton(new SimpleGrantedAuthority(employeeDto.getRole().name()))
    );
  }
}