package com.douzon.blooming.token.service;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.exception.EmployeePermissionDefinedException;
import com.douzon.blooming.employee.dto.response.ResponseEmployeeDto;
import com.douzon.blooming.employee.exception.NotFoundEmployeeException;
import com.douzon.blooming.employee.repo.EmployeeRepository;
import com.douzon.blooming.token.RefreshToken;
import com.douzon.blooming.token.exception.NotFoundRefreshTokenException;
import com.douzon.blooming.token.provider.TokenProvider;
import com.douzon.blooming.token.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

  private final AuthenticationManagerBuilder managerBuilder;
  private final RefreshTokenRepository refreshTokenRepository;
  private final EmployeeRepository employeeRepository;
  private final TokenProvider tokenProvider;
  private final UserDetailsService userDetailsService; // UserDetailsService 주입

  public String reissueAccessToken(String refreshToken, Long employeeNo) {
    RefreshToken token = refreshTokenRepository.findByRefreshToken(employeeNo)
        .orElseThrow(NotFoundRefreshTokenException::new);
    ResponseEmployeeDto dto = employeeRepository.findEmployeeByNo(employeeNo)
        .orElseThrow(NotFoundEmployeeException::new);

    if(!refreshToken.equals(token.getToken())) {
      throw new NotFoundRefreshTokenException();
    }

    if (token.getEmployeeNo().equals(employeeNo)) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getId());
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());;
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      return tokenProvider.createAccessToken(authenticationToken);
    }
    throw new NotFoundRefreshTokenException();
  }

  public TokenDto getToken(String id, String password, Long employeeNo) {
    Authentication authentication = createAuthentication(id, password);

    String accessToken = tokenProvider.createAccessToken(authentication);
    String refreshToken = tokenProvider.createRefreshToken(authentication);

    refreshTokenRepository.save(new RefreshToken(refreshToken, employeeNo));

    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .grantType("Bearer")
        .build();
  }

  public void removeRefreshToken(RefreshToken refreshToken) {
    RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken.getEmployeeNo())
        .orElseThrow(NotFoundRefreshTokenException::new);
    if(token.getToken().equals(refreshToken.getToken())){
      refreshTokenRepository.delete(refreshToken);
    }
    throw new EmployeePermissionDefinedException();
  }

  private Authentication createAuthentication(String id, String password) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id,
        password);
    return managerBuilder.getObject().authenticate(token);
  }
}
