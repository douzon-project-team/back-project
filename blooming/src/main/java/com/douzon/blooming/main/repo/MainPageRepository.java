package com.douzon.blooming.main.repo;

import com.douzon.blooming.main.dto.request.MainPageDataDto;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MainPageRepository {

  private static final String MAIN_PAGE_KEY = "main-page";
  private final RedisTemplate<String, MainPageDataDto> redisTemplate;

  public void save(final MainPageDataDto mainPageDataDto) {
    ValueOperations<String, MainPageDataDto> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(MAIN_PAGE_KEY, mainPageDataDto);
    redisTemplate.expire(MAIN_PAGE_KEY, 3600L, TimeUnit.SECONDS);
  }

  public Optional<MainPageDataDto> find() {
    ValueOperations<String, MainPageDataDto> valueOperations = redisTemplate.opsForValue();
    MainPageDataDto mainPageDataDto = valueOperations.get(MAIN_PAGE_KEY);

    if (Objects.isNull(mainPageDataDto)) {
      return Optional.empty();
    }

    return Optional.of(mainPageDataDto);
  }

}
