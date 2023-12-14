package com.douzon.blooming.product_instruction.exception;

public class UnsupportedProductStatusException extends RuntimeException {
    public UnsupportedProductStatusException() {
        super("지원하지 않는 프로덕트 타입입니다.");
    }
}
