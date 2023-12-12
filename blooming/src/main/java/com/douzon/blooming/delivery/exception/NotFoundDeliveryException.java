package com.douzon.blooming.delivery.exception;

public class NotFoundDeliveryException extends RuntimeException{
    public NotFoundDeliveryException() {
        super("해당 출고를 찾을수 없습니다.");
    }
}
