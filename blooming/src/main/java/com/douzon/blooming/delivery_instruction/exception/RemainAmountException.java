package com.douzon.blooming.delivery_instruction.exception;

public class RemainAmountException extends RuntimeException {
    public RemainAmountException() {
        super("수량이 잔량보다 많습니다.");
    }
}
