package com.douzon.blooming.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String EVENT_TOPIC = "blooming-events";
    private static final String MESSAGE_TOPIC = "blooming-messages";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendCRUDEvent(String event) {
        kafkaTemplate.send(EVENT_TOPIC, event);
    }

    public void sendMessage(String event) {
        kafkaTemplate.send(MESSAGE_TOPIC, event);
    }
}