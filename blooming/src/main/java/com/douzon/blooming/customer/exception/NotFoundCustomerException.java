package com.douzon.blooming.customer.exception;

public class NotFoundCustomerException extends RuntimeException{
    public NotFoundCustomerException() {
        super("Not Found Customer");
    }
}
