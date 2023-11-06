package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveriesDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.service.DeliveryService;
import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Void> addDelivery(@RequestBody InsertDeliveryDto dto){
        deliveryService.addDelivery(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<GetDeliveriesDto> getDeliveries(@ModelAttribute DeliverySearchDto dto){
        return ResponseEntity.ok().body(deliveryService.findDeliveries(dto));
    }

    @GetMapping("/{deliveryNo}")
    public ResponseEntity<GetDeliveryDto> getDelivery(@PathVariable String deliveryNo){
        return ResponseEntity.ok().body(deliveryService.findDelivery(deliveryNo));
    }

    @GetMapping("/{deliveryNo}/{instructionNo}")
    public ResponseEntity<List<GetInstructionDetailDto>> getDeliveryDetail(@PathVariable String deliveryNo,
                                                                           @PathVariable String instructionNo){
        return ResponseEntity.ok().body(deliveryService.findDeliveryDetail(deliveryNo, instructionNo));
    }

    @PutMapping("/{deliveryNo}}")


    @DeleteMapping("/{deliveryNo}")
    public ResponseEntity<Void> removeDelivery(@PathVariable String deliveryNo){
        deliveryService.removeDelivery(deliveryNo);
        return ResponseEntity.noContent().build();
    }
}
