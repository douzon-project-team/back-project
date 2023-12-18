package com.douzon.blooming.token.repo;

import com.douzon.blooming.token.RefreshToken;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

  private final RedisTemplate<String, String> redisTemplate;

  public void save(final RefreshToken refreshToken) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(refreshToken.getEmployeeNo().toString(), refreshToken.getToken());
    redisTemplate.expire(refreshToken.getEmployeeNo().toString(), 86400L, TimeUnit.SECONDS);
  }

  public Optional<RefreshToken> findByRefreshToken(final Long employeeNo) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    String refreshToken = valueOperations.get(employeeNo.toString());

    if (Objects.isNull(refreshToken)) {
      return Optional.empty();
    }

    return Optional.of(new RefreshToken(refreshToken, employeeNo));
  }

  public void delete(RefreshToken refreshToken) {
    redisTemplate.delete(refreshToken.getEmployeeNo().toString());
  }
}
