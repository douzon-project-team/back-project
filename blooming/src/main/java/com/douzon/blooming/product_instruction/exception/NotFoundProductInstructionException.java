package com.douzon.blooming.product_instruction.exception;

public class NotFoundProductInstructionException extends
    RuntimeException {

  public NotFoundProductInstructionException() {
    super("not found product-instruction");
  }
}
