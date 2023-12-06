package com.douzon.blooming.kafka.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InsertMessageDto {
    private String sendId;
    private String sendName;
    private String targetId;
    private String targetName;
    private String message;
    private String sendTime;
}
