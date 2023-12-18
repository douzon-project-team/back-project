package com.douzon.blooming.instruction.dto;


import java.util.Arrays;

public enum ProgressStatus {
    STANDBY, PROGRESS, COMPLETED;

    public static ProgressStatus fromProgressStatusName(String name) {
        return Arrays.stream(values())
                .filter(progressStatus -> progressStatus.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Progress status name :" + name));
    }
}
