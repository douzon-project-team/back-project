package com.douzon.blooming.token.provider;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.exception.TokenMissingAuthorizationInfoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private final Key key;
  @Value("${jwt.secret.access-expire-time}")
  private Long accessTokenExpireTime;
  @Value("${jwt.secret.refresh-expire-time}")
  private Long refreshTokenExpireTime;

  public TokenProvider(@Value("${jwt.secret.symbol}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // 토큰 생성
  // 인증을 성공하면 AbstractUserDetailsAuthenticationProvider 에서 createSuccessAuthication 메서드를 통해 principal 에 userDetails를 넣어줌
  // credential 은 protect 라 널임

  /**
   * Access 토큰 생성
   * @param authentication
   * @return
   */
  public String createAccessToken(Authentication authentication) {
    EmployeeDetails principal = (EmployeeDetails) authentication.getPrincipal();
    String authorities = principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    return Jwts
        .builder()
        .subject(authentication.getName())
        .claims(Map.of(AUTHORITIES_KEY, authorities, "employeeNo", principal.getEmployeeNo(), "name", principal.getName()))
        .expiration(new Date(System.currentTimeMillis() + accessTokenExpireTime))
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
  }

  /**
   * refresh 토큰 생성
   * @param authentication
   * @return
   */
  public String createRefreshToken(Authentication authentication) {
    return Jwts.builder()
        .subject(authentication.getName())
        .expiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime))
        .signWith(SignatureAlgorithm.HS256, key)
        .compact();
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new TokenMissingAuthorizationInfoException();
    }

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    EmployeeDetails principal = new EmployeeDetails(
        Long.parseLong(claims.get("employeeNo").toString()),claims.get("name").toString(),
        claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  /**
   * 토큰 검증
   * @param token
   * @return
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception ignore) {
    }
    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parser().setSigningKey(key).build().parseClaimsJws(accessToken).getPayload();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
