package com.douzon.blooming.kafka.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@ToString
public class MessageDto {
    private Integer messageNo;
    private Long sendId;
    private String sendName;
    private Long targetId;
    private String targetName;
    private String message;
    private String sendTime;
}
