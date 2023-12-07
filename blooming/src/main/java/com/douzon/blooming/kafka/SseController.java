package com.douzon.blooming.kafka;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.kafka.message.dto.InsertMessageDto;
import com.douzon.blooming.kafka.message.repo.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/sse")
@Slf4j
public class SseController {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    private final MessageRepository messageRepository;

    @Autowired
    public SseController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Long clientId = employeeDetails.getEmployeeNo();

        SseEmitter emitter = new SseEmitter(60*60*1000L);
        emitters.put(clientId, emitter);

        emitter.onTimeout(emitter::complete);

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            String formattedDate = LocalDateTime.now().format(formatter);
            emitters.forEach((clientId, emitter) -> {
                try {
                    // 데이터를 클라이언트에게 전송하는 데 사용 (data, mediaType)
                    emitter.send(SseEmitter.event()
                            .name("crudEvent")
                            .data(event+","+ formattedDate)
                            .id("uniqueEventId"));
                    // 요청의 처리가 완료되었음을 나타내는 handler
                    // 이 메서드를 호출한 후에는 Handler와의 추가 상호 작용이 허용되지 않는다.
                    emitter.complete();
                } catch (IOException e) {
                    // 요청 처리 중에 발생하는 오류 handler
                    emitter.completeWithError(e);
                }
            });
        }

        @KafkaListener(topics = "blooming-messages", groupId = "my-group")
        public void consumerMessageEvent(String message) {
            String[] splitMessage = message.split("&&");
            saveMessageToDatabase(splitMessage[0], splitMessage[1], splitMessage[2], splitMessage[3], splitMessage[4]);

            emitters.forEach((clientId, emitter) -> {
                if(Objects.equals(clientId.toString(), splitMessage[2])) {
                    try {
                        emitter.send(SseEmitter.event()
                                .name("messageEvent")
                                .data(message)
                                .id("uniqueEventId"));
                        emitter.complete();
                    } catch (IOException e) {
                        emitter.completeWithError(e);
                    }
                }
            });
        }

        private void saveMessageToDatabase(String sendId, String sendName, String targetId, String targetName, String message) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            InsertMessageDto userMessage = new InsertMessageDto(
                    sendId, sendName, targetId, targetName, message, currentDateTime.format(formatter));
            messageRepository.save(userMessage);
        }
    }
}