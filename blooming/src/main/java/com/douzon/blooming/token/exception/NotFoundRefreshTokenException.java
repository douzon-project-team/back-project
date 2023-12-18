package com.douzon.blooming.token.exception;

public class NotFoundRefreshTokenException extends RuntimeException {

  public NotFoundRefreshTokenException() {
    super("존재하지 않는 리프레시 토큰입니다.");
  }
}
