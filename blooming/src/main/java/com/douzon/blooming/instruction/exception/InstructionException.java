package com.douzon.blooming.instruction.exception;

public class InstructionException extends RuntimeException {
    enum InstructionExceptionType {
        UNMODIFIABLE("unmodifiable instruction");

        private String message;

        InstructionExceptionType(String s) {
            this.message = s;
        }
    }
    public InstructionException() {
        super("Instruction not found");
    }

    public InstructionException(InstructionExceptionType type) {
        super(type.message);
    }
}