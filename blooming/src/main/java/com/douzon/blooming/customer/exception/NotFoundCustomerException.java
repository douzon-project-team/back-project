package com.douzon.blooming.customer.exception;

public class NotFoundCustomerException extends RuntimeException{
    public NotFoundCustomerException() {
        super("해당 거래처를 찾을수 없습니다.");
    }
}
