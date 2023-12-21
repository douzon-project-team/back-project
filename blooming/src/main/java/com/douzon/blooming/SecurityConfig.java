package com.douzon.blooming;

import com.douzon.blooming.auth.advice.JwtAccessDeniedHandler;
import com.douzon.blooming.auth.advice.JwtAuthenticationEntryPoint;
import com.douzon.blooming.token.provider.TokenProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000","https://blooming-eytrri2ab-sangwoongparks-projects.vercel.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders(HttpHeaders.LOCATION, HttpHeaders.SET_COOKIE)
            .allowCredentials(true);
      }
    };
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()

        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .cors()

        .and()
        .headers()
        .frameOptions()
        .sameOrigin()

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/employees/login", "/token/reissue", "/employees/logout", "/employees/*/image").permitAll()
        .antMatchers("/auth/employees/**").hasRole("ADMIN")
//        .antMatchers("/sse/**").permitAll()
        .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
        .and()
        .apply(new JwtSecurityConfig(tokenProvider));

    return http.build();
  }
}