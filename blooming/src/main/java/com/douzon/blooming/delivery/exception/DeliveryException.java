package com.douzon.blooming.delivery.exception;

public class DeliveryException extends RuntimeException{
    enum DeliveryExceptionType {
        UNMODIFIABLE("unmodifiable delivery");

        private String message;

        DeliveryExceptionType(String s) {
            this.message = s;
        }
    }
    public DeliveryException() {
        super("delivery not found");
    }

    public DeliveryException(DeliveryExceptionType type) {
        super(type.message);
    }
}
