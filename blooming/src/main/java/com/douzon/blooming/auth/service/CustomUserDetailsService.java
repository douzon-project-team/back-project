package com.douzon.blooming.auth.service;

import com.douzon.blooming.auth.dto.response.EmployeeDto;
import com.douzon.blooming.auth.exception.NotFoundEmployeeAuthException;
import com.douzon.blooming.auth.repo.EmployeeAuthRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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
        .map(this::createUserDetails)
        .orElseThrow(NotFoundEmployeeAuthException::new);
  }

  private UserDetails createUserDetails(EmployeeDto employeeDto) {
    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
        employeeDto.getRole().name());
    return new User(
        employeeDto.getId(),
        employeeDto.getPassword(),
        Collections.singleton(grantedAuthority));
  }
}