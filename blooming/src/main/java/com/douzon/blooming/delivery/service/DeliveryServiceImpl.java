package com.douzon.blooming.delivery.service;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.auth.EmployeeDetails;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.delivery.dto.request.DeliverySearchDto;
import com.douzon.blooming.delivery.dto.request.RequestDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery.dto.response.GetDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ResponseDeliveryDto;
import com.douzon.blooming.delivery.dto.response.ResponseMyDeliveryDto;
import com.douzon.blooming.delivery.exception.NotFoundDeliveryException;
import com.douzon.blooming.delivery.repo.DeliveryRepository;
import com.douzon.blooming.delivery_instruction.dto.response.DeliveryListInstructionDto;
import com.douzon.blooming.delivery_instruction.repo.DeliveryInstructionRepository;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.time.LocalDate.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryInstructionRepository deliveryInstructionRepository;

  @Override
  public ResponseDeliveryDto addDelivery(RequestDeliveryDto dto) {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    DeliveryStatus status= dto.getDeliveryDate().isBefore(now())? DeliveryStatus.COMPLETE : DeliveryStatus.INCOMPLETE;
    deliveryRepository.insertDelivery(employeeDetails.getEmployeeNo(), dto, status);
    return new ResponseDeliveryDto(deliveryRepository.getDeliveryNo());
  }

  @Override
  public GetDeliveryDto findDelivery(String deliveryNo) {
    GetDeliveryDto getDeliveryDto = deliveryRepository.findDelivery(deliveryNo)
            .orElseThrow(NotFoundDeliveryException::new);
    getDeliveryDto.setInstructions(
            deliveryInstructionRepository.findInstructionsByDeliverNo(deliveryNo));
    return getDeliveryDto;
  }

  @Override
  public PageDto<DeliveryListInstructionDto> findDeliveries(DeliverySearchDto searchDto) {
    log.error(searchDto.toString());
    log.info(searchDto.getPage()+"AND"+ searchDto.getPageSize());
    int start = (searchDto.getPage()) * searchDto.getPageSize();
    List<DeliveryListInstructionDto> deliveries = deliveryRepository.findDeliveries(searchDto,
            start, searchDto.getPageSize());
    deliveries.forEach(delivery -> {
      delivery.setInstructionCount(
              deliveryInstructionRepository.getInstructionCount(delivery.getDeliveryNo()));
    });

    int searchInstructionCount = deliveryRepository.getCountDeliveries(searchDto);

    return PageDto.<DeliveryListInstructionDto>builder().list(deliveries)
            .currentPage(searchDto.getPage() + 1)
            .hasNextPage(start + searchDto.getPageSize() < searchInstructionCount)
            .hasPreviousPage(start > 0)
            .build();
  }

  @Override
  public void updateDelivery(String deliveryNo, UpdateDeliveryDto dto) {
    if (deliveryRepository.updateDelivery(deliveryNo, dto) <= 0) {
      throw new NotFoundDeliveryException();
    }
  }

  @Override
  public void removeDelivery(String deliveryNo) {
    if (deliveryRepository.deleteDelivery(deliveryNo) <= 0) {
      throw new NotFoundDeliveryException();
    }
  }

  @Override
  public void changeStatus(String deliveryNo) {
    deliveryRepository.changeStatus(deliveryNo);
  }

  @Override
  public ResponseMyDeliveryDto findMyDelivery() {
    EmployeeDetails employeeDetails = (EmployeeDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    return deliveryRepository.findMyDelivery(employeeDetails.getEmployeeNo());
  }

}