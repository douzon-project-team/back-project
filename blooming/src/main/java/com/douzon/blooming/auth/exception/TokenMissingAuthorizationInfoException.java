package com.douzon.blooming.auth.exception;

public class TokenMissingAuthorizationInfoException extends RuntimeException {

  public TokenMissingAuthorizationInfoException() {
    super("잘못된 요청입니다.");
  }

}
