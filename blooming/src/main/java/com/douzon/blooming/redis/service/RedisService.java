package com.douzon.blooming.redis.service;

import com.douzon.blooming.auth.dto.response.TokenDto;

public interface RedisService {
  void addToken(TokenDto tokenDto);
  void getToken(String id);
}
