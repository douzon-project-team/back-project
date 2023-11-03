package com.douzon.blooming.product_instruction.exception;

public class UnsupportedProductStatusException extends RuntimeException {
    public UnsupportedProductStatusException() {
        super("Unsupported product_status!!");
    }
}
