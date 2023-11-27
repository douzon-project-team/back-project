package com.douzon.blooming.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/sse")
@Slf4j
public class SseController {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter();

        String clientId = UUID.randomUUID().toString();
        emitters.put(clientId, emitter);

        emitter.onCompletion(()
                -> emitters.remove(clientId));
        emitter.onError((ex)
                -> emitters.remove(clientId));
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
            //503 에러 방지를 위한 더미 데이터
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.error(emitters.toString());
        return emitter;
    }

    @Async
    @Service
    public class KafkaConsumerService {

        @KafkaListener(topics = "blooming-events", groupId = "my-group")
        public void consumerCRUDEvent(String event) {
            emitters.forEach((clientId, emitter) -> {
                try {
                    // 데이터를 클라이언트에게 전송하는 데 사용 (data, mediaType)
                    emitter.send(event, MediaType.APPLICATION_JSON);
                    // 요청의 처리가 완료되었음을 나타내는 handler
                    // 이 메서드를 호출한 후에는 Handler와의 추가 상호 작용이 허용되지 않는다.
                    emitter.complete();
                } catch (IOException e) {
                    // 요청 처리 중에 발생하는 오류 handler
                    emitter.completeWithError(e);
                }
            });
        }
    }
}