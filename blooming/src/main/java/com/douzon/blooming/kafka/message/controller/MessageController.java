package com.douzon.blooming.kafka.message.controller;

import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.kafka.KafkaProducerService;
import com.douzon.blooming.kafka.message.dto.RequestMessageDto;
import com.douzon.blooming.kafka.message.dto.ResponseMessageDto;
import com.douzon.blooming.kafka.message.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/employees/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final KafkaProducerService kafkaProducerService;
    private final MessageRepository messageRepository;
    private final EmployeeService employeeService;

    @GetMapping("/getMessages")
    public ResponseEntity<ResponseMessageDto> getMessages(){
        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String clientId = employeeDetails.getUsername();
        System.out.println(messageRepository.findMessagesById(clientId));
        return ResponseEntity.ok().body(new ResponseMessageDto(messageRepository.findMessagesById(clientId)));
    }

    @PostMapping("/{sendId}/{targetId}")
    public ResponseEntity<Void> sendMessage(@PathVariable Long sendId,
                                      @PathVariable Long targetId,
                                      @RequestBody RequestMessageDto message){
        log.info(message.toString());
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        kafkaProducerService.sendMessage(sendId+","+employeeService.getEmployeeByNo(sendId).getName()+","
                +targetId+","+employeeService.getEmployeeByNo(targetId).getName()+","+message.getMessage()+","+currentDateTime.format(formatter));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{messageNo}")
    public ResponseEntity<Void> checkMessage(@PathVariable Long messageNo){
        log.info("Checking message");
        messageRepository.checkMessage(messageNo);
        return ResponseEntity.ok().build();
    }
}
