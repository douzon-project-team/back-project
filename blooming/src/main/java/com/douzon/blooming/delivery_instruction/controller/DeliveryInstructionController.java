package com.douzon.blooming.delivery_instruction.controller;

import com.douzon.blooming.delivery_instruction.dto.request.DeleteDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.service.DeliveryInstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-instructions")
public class DeliveryInstructionController {
    private final DeliveryInstructionService service;

    @PostMapping("/{deliveryNo}")
    public ResponseEntity<Void> addDeliveryInstructions(@RequestBody InsertDeliveryInstructionDto dto,
                                                        @PathVariable String deliveryNo){
        service.addDeliveryInstructions(deliveryNo, dto);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{deliveryNo}")
    public ResponseEntity<Void> updateDeliveryInstructions(@RequestBody UpdateDeliveryInstructionProductDto dto,
                                                           @PathVariable String deliveryNo){
        service.updateDeliveryInstructions(deliveryNo, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{deliveryNo}")
    public ResponseEntity<Void> deleteDeliveryInstructions(@RequestBody DeleteDeliveryInstructionProductDto dto,
                                                           @PathVariable String deliveryNo){
        service.deleteDeliveryInstructions(deliveryNo, dto);
        return ResponseEntity.noContent().build();
    }
}
