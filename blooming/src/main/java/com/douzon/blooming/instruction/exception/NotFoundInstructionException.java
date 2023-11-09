package com.douzon.blooming.instruction.exception;

public class NotFoundInstructionException extends RuntimeException {
    public NotFoundInstructionException() {
        super("Instruction not found");
    }
}
