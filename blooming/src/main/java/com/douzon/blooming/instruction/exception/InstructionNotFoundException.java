package com.douzon.blooming.instruction.exception;

public class InstructionNotFoundException extends RuntimeException {
    public InstructionNotFoundException() {
        super("Instruction not found");
    }
}
