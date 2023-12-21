package com.douzon.blooming.instruction.exception;

public class DeadLockException extends RuntimeException{
    public DeadLockException() {
        super("현재 요청이 많습니다. 잠시후에 다시 시도해주세요.");
    }
}
