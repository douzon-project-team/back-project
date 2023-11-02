package com.douzon.blooming.instruction.dto;

import com.douzon.blooming.auth.EmployeeRole;
import com.douzon.blooming.instruction.StatusTypeHandler;
import org.apache.ibatis.type.MappedTypes;

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
