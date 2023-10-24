package com.douzon.blooming.redis.service;

import com.douzon.blooming.auth.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate redisTemplate;

  @Override
  public void addToken(TokenDto tokenDto) {

  }

  @Override
  public void getToken(String id) {

  }
}
