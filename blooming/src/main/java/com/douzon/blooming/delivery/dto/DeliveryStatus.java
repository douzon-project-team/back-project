package com.douzon.blooming.delivery.dto;

import java.util.Arrays;

public enum DeliveryStatus {
    INCOMPLETE, COMPLETED;

    public static DeliveryStatus fromDeliveryStatusName(String name) {
        return Arrays.stream(values())
                .filter(progressStatus -> progressStatus.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Progress status name :" + name));
    }
}
