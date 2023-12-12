package com.douzon.blooming.instruction.exception;

public class NotFoundInstructionException extends RuntimeException {
    public NotFoundInstructionException() {
        super("해당 지시를 찾을 수 없습니다.");
    }
}
