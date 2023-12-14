package com.douzon.blooming.delivery_instruction.service;

import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateInstructionProductDto;
import com.douzon.blooming.delivery_instruction.repo.DeliveryInstructionRepository;
import com.douzon.blooming.product.exception.NotFoundProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryInstructionServiceImpl implements DeliveryInstructionService{

    private final DeliveryInstructionRepository deliveryInstructionRepository;
    @Override
    public void addDeliveryInstructions(String deliveryNo, InsertDeliveryInstructionDto dto) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("INSERT INTO project.delivery_instruction VALUES ");
        dto.getProducts().forEach(product -> {
            stringBuffer.append("('")
                    .append(deliveryNo)
                    .append("', '")
                    .append(dto.getInstructionNo())
                    .append("', ")
                    .append(product.getProductNo())
                    .append(", ")
                    .append(product.getAmount())
                    .append("),");
        });

        stringBuffer.setLength(stringBuffer.length()-1);
        stringBuffer.append(";");

        String insertQuery = stringBuffer.toString();
        deliveryInstructionRepository.insert(insertQuery);
    }

    @Override
    public void updateDeliveryInstructions(String deliveryNo, UpdateInstructionProductDto dto) {
        if(deliveryInstructionRepository.updateProduct(deliveryNo, dto.getInstructionNo(), dto.getProductNo(), dto.getAmount()) <= 0){
            throw new NotFoundProductException();
        };
    }

    @Override
    public void deleteDeliveryInstructions(String deliveryNo, String instructionNo, String productNo) {
        if(deliveryInstructionRepository.deleteProduct(deliveryNo, instructionNo, productNo)<=0){
            throw new NotFoundProductException();
        };
    }
}