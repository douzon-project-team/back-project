package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliverys")
public class DeliveryController {
    private final DeliveryService deliveryService;

//    @PostMapping("/insert")
//    public ResponseEntity<Void> addDelivery(@RequestBody )
}
