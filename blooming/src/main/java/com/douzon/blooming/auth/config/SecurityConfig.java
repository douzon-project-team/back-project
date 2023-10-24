package com.douzon.blooming.auth.config;

import com.douzon.blooming.auth.advice.JwtAccessDeniedHandler;
import com.douzon.blooming.auth.advice.JwtAuthenticationEntryPoint;
import com.douzon.blooming.auth.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
    return NoOpPasswordEncoder.getInstance();
  }

  // h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring()
//        .antMatchers("/h2-console/**", "/favicon.ico");
//  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()

        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .headers()
        .frameOptions()
        .sameOrigin()

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/auth/register").hasRole("ADMIN")
        .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
        .and()
        .apply(new JwtSecurityConfig(tokenProvider));

    return http.build();
  }
}