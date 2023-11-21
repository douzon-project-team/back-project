package com.douzon.blooming.auth.filter;

import com.douzon.blooming.token.provider.TokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws IOException, ServletException {

    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (validBearerToken(bearerToken)) {
      String jwt = getJsonWebToken(bearerToken);
      if (tokenProvider.validateToken(jwt)) {
        Authentication authentication = tokenProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

  private boolean validBearerToken(String bearerToken) {
    return StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX);
  }

  private String getJsonWebToken(String bearerToken) {
    return bearerToken.substring(7);
  }
}