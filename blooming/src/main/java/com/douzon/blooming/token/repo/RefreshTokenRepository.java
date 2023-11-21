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

  private final RedisTemplate<String, Long> redisTemplate;

  public void save(final RefreshToken refreshToken) {
    ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(refreshToken.getToken(), refreshToken.getEmployeeNo());
    redisTemplate.expire(refreshToken.getToken(), 86400L, TimeUnit.SECONDS);
  }

  public Optional<RefreshToken> findByRefreshToken(final String refreshToken) {
    ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
    Long employeeNo = valueOperations.get(refreshToken);

    if (Objects.isNull(employeeNo)) {
      return Optional.empty();
    }

    return Optional.of(new RefreshToken(refreshToken, employeeNo));
  }

  public void delete(RefreshToken refreshToken) {
    redisTemplate.delete(refreshToken.getToken());
  }
}
