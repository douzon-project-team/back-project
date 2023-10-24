package com.douzon.blooming.auth.exception;

public class TokenMissingAuthorizationInfoException extends RuntimeException {

  public TokenMissingAuthorizationInfoException() {
    super("Token Missing Authorization info");
  }

}
