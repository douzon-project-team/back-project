package com.douzon.blooming.kafka.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseMessageDto {

    List<MessageDto> messages;
}
