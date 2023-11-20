package com.douzon.blooming.delivery_instruction.service;

import com.douzon.blooming.delivery.repo.DeliveryRepository;
import com.douzon.blooming.delivery_instruction.dto.request.DeleteDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.repo.DeliveryInstructionRepository;
import com.douzon.blooming.product.exception.NotFoundProductException;
import com.douzon.blooming.product_instruction.exception.UnsupportedProductStatusException;
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
    public void updateDeliveryInstructions(String deliveryNo, UpdateDeliveryInstructionProductDto dto) {
        dto.getProducts().forEach(product -> {
            switch (product.getStatus()) {
                case "added":
                    deliveryInstructionRepository.insertProduct(deliveryNo, dto.getInstructionNo(), product);
                    break;
                case "updated":
                    deliveryInstructionRepository.updateProduct(deliveryNo, dto.getInstructionNo(), product);
                    break;
                default:
                    throw new UnsupportedProductStatusException();
            }
        });
    }

    @Override
    public void deleteDeliveryInstructions(String deliveryNo, DeleteDeliveryInstructionProductDto dto) {
        if(deliveryInstructionRepository.deleteProduct(deliveryNo, dto)<=0){
            throw new NotFoundProductException();
        };
    }
}
