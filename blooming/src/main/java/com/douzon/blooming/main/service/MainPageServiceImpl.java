package com.douzon.blooming.main.service;

import com.douzon.blooming.customer.repo.CustomerRepository;
import com.douzon.blooming.delivery.repo.DeliveryRepository;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.main.dto.request.BarGraphDto;
import com.douzon.blooming.main.dto.request.CircleGraphDto;
import com.douzon.blooming.main.dto.request.CurrentSituation;
import com.douzon.blooming.main.dto.request.MainPageDataDto;
import com.douzon.blooming.main.repo.MainPageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MainPageServiceImpl implements MainPageService {

  private final CustomerRepository customerRepository;
  private final InstructionRepository instructionRepository;
  private final DeliveryRepository deliveryRepository;
  private final MainPageRepository mainPageRepository;

  @Override
  public MainPageDataDto getMainPageData() {
    return mainPageRepository.find().orElse(renewMainPageDataDto());
  }

  @Override
  public List<BarGraphDto> getMainPageBarGraphData(String type) {
    return instructionRepository.findMainPageBarGraphData(type);
  }

  @Override
  public List<CircleGraphDto> getMainPageCircleGraphData(String type) {
    return instructionRepository.findMainPageCircleGraphData(type);
  }

  private MainPageDataDto renewMainPageDataDto() {
    CurrentSituation instruction = new CurrentSituation(
        instructionRepository.findThisMonthInstructionCount(),
        instructionRepository.findInstructionCount());

    CurrentSituation delivery = new CurrentSituation(
        deliveryRepository.findThisMonthDeliveryCount(),
        deliveryRepository.findDeliveryCount());

    MainPageDataDto mainPageDataDto = MainPageDataDto.builder()
        .customer(customerRepository.findManyTransactionCustomers())
        .delivery(delivery)
        .instruction(instruction)
        .expirationDateNearInstruction(instructionRepository.findExpirationDateNearInstruction())
        .build();

    mainPageRepository.save(mainPageDataDto);
    return mainPageDataDto;
  }
}
