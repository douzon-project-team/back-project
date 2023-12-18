package com.douzon.blooming.log.exception;

public class MethodNotSupportedException extends RuntimeException {
    public MethodNotSupportedException(String method) {
        super(method + "는 지원하지 않는 메소드 타입입니다.");
    }
}
