package com.douzon.blooming.product.exception;

public class NotFoundProductException extends RuntimeException {

  public NotFoundProductException() {
    super("존재하지 않는 상품입니다.");
  }
}
