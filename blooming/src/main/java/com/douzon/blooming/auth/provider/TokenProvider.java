package com.douzon.blooming.auth.provider;

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
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_TYPE = "bearer";
  @Value("${jwt.secret.access-expire-time}")
  private Long accessTokenExpireTime;
  private final Key key;

  public TokenProvider(@Value("${jwt.secret.symbol}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // 토큰 생성
  public TokenDto generateToken(Authentication authentication) {

    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();

    Date tokenExpiresIn = new Date(now + accessTokenExpireTime);

    String accessToken = Jwts.builder()
        .subject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .expiration(tokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return TokenDto.builder()
        .grantType(BEARER_TYPE)
        .accessToken(accessToken)
        .build();
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

    UserDetails principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

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
