package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.RequestDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery.dto.response.*;
import com.douzon.blooming.delivery.service.DeliveryService;
import com.douzon.blooming.delivery_instruction.dto.response.DeliveryListInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<ResponseDeliveryDto> addDelivery(@RequestBody RequestDeliveryDto dto){
        return ResponseEntity.ok().body(deliveryService.addDelivery(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<PageDto<DeliveryListInstructionDto>> getDeliveries(@ModelAttribute DeliverySearchDto dto){
        return ResponseEntity.ok().body(deliveryService.findDeliveries(dto));
    }

    @GetMapping("/{deliveryNo}")
    public ResponseEntity<GetDeliveryDto> getDelivery(@PathVariable String deliveryNo){
        return ResponseEntity.ok().body(deliveryService.findDelivery(deliveryNo));
    }

    @PutMapping("/{deliveryNo}")
    public ResponseEntity<Void> updateDelivery(@PathVariable String deliveryNo,
                                               @RequestBody UpdateDeliveryDto dto){
        deliveryService.updateDelivery(deliveryNo, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{deliveryNo}")
    public ResponseEntity<Void> removeDelivery(@PathVariable String deliveryNo){
        deliveryService.removeDelivery(deliveryNo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{deliveryNo}/complete")
    public ResponseEntity<Void> completeDelivery(@PathVariable String deliveryNo){
        deliveryService.changeStatus(deliveryNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/myDelivery")
    public ResponseEntity<ResponseMyDeliveryDto> getMyDelivery(){
        return ResponseEntity.ok().body(deliveryService.findMyDelivery());
    }
}
