package com.douzon.blooming.token.exception;

public class NotFoundRefreshTokenException extends RuntimeException {

  public NotFoundRefreshTokenException() {
    super("not found refresh token exception");
  }
}
