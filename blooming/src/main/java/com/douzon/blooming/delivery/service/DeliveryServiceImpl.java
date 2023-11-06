package com.douzon.blooming.delivery.service;

import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveriesDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ListDeliveryDto;
import com.douzon.blooming.delivery.repo.DeliveryRepository;
import com.douzon.blooming.delivery_instruction.dto.response.GetInstructionDetailDto;
import com.douzon.blooming.delivery_instruction.repo.DeliveryInstructionRepository;
import com.douzon.blooming.instruction.dto.response.GetInstructionListDto;
import com.douzon.blooming.instruction.dto.response.ListInstructionDto;
import com.douzon.blooming.product.dto.SearchProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final DeliveryInstructionRepository deliveryInstructionRepository;

    @Override
    public void addDelivery(InsertDeliveryDto dto) {
//        EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        deliveryRepository.insertDelivery(employeeDetails.getEmployeeNo(), dto);
        deliveryRepository.insertDelivery(15L, dto);
        String deliveryNo = deliveryRepository.getDeliveryNo();
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("INSERT INTO project.delivery_instruction VALUES ");
        dto.getInstructions().forEach(instruction -> {
            instruction.getProducts().forEach(product -> {
                stringBuffer.append("('")
                        .append(deliveryNo)
                        .append("', '")
                        .append(instruction.getInstructionNo())
                        .append("', ")
                        .append(product.getProductNo())
                        .append(", ")
                        .append(product.getAmount())
                        .append("),");
            });
        });
        stringBuffer.setLength(stringBuffer.length()-1);
        stringBuffer.append(";");

        String insertQuery = stringBuffer.toString();
        deliveryInstructionRepository.insert(insertQuery);
    }

    @Override
    public GetDeliveryDto findDelivery(String deliveryNo) {
        GetDeliveryDto getDeliveryDto = deliveryRepository.findDelivery(deliveryNo)
                .orElseThrow(RuntimeException::new);
        getDeliveryDto.setInstructions(deliveryInstructionRepository.findInstructionsByDeliverNo(deliveryNo));
        return getDeliveryDto;
    }

    @Override
    public List<GetInstructionDetailDto> findDeliveryDetail(String deliveryNo, String instructionNo) {
        return deliveryInstructionRepository.findDeliveryDetail(deliveryNo, instructionNo);
    }


    @Override
    public GetDeliveriesDto findDeliveries(DeliverySearchDto searchDto) {
        searchDto.setPage(Math.max(searchDto.getPage(), DeliverySearchDto.DEFAULT_PAGE));
        searchDto.setPageSize(Math.max(searchDto.getPageSize(), DeliverySearchDto.DEFAULT_PAGE_SIZE));
        int start = (searchDto.getPage() - 1) * searchDto.getPageSize();
        List<ListDeliveryDto> deliveries = deliveryRepository.findDeliveries(searchDto, start, searchDto.getPageSize());
        int searchInstructionCount = deliveryRepository.getCountDeliveries(searchDto);

        boolean hasNextPage = (start + searchDto.getPageSize()) < searchInstructionCount;
        boolean hasPreviousPage = start > 0;

        return new GetDeliveriesDto(deliveries, searchDto.getPage(), hasNextPage, hasPreviousPage);
    }

    @Override
    public void removeDelivery(String deliveryNo) {
        if(deliveryRepository.deleteDelivery(deliveryNo) <= 0){
            throw new IllegalStateException();
        }
    }

}
