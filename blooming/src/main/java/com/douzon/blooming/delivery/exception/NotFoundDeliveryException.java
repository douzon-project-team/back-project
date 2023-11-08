package com.douzon.blooming.delivery.exception;

public class NotFoundDeliveryException extends RuntimeException{
    public NotFoundDeliveryException() {
        super("Delivery not found");
    }
}
