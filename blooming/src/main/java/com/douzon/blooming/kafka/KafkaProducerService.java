package com.douzon.blooming.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    private static final String TOPIC = "blooming-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCRUDEvent(String event) {
        log.error(event);
        kafkaTemplate.send(TOPIC, event);
    }
}