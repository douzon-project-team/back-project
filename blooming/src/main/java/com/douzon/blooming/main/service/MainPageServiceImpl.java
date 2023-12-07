package com.douzon.blooming.main.service;

import com.douzon.blooming.customer.repo.CustomerRepository;
import com.douzon.blooming.delivery.repo.DeliveryRepository;
import com.douzon.blooming.instruction.repo.InstructionRepository;
import com.douzon.blooming.main.dto.BarGraphDto;
import com.douzon.blooming.main.dto.CircleGraphDto;
import com.douzon.blooming.main.dto.CurrentSituation;
import com.douzon.blooming.main.dto.request.BarGraphListDto;
import com.douzon.blooming.main.dto.request.CircleGraphListDto;
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
  public BarGraphListDto getMainPageBarGraphData(String type) {
    List<BarGraphDto> instructionData = instructionRepository.findMainPageBarGraphData(type);
    List<BarGraphDto> deliveryData = deliveryRepository.findMainPageBarGraphData(type);

    return BarGraphListDto.builder()
        .instructionData(instructionData)
        .deliveryData(deliveryData)
        .build();
  }

  @Override
  public CircleGraphListDto getMainPageCircleGraphData(String type) {
    List<CircleGraphDto> instructionData = instructionRepository.findMainPageCircleGraphData(type);
    List<CircleGraphDto> deliveryData = deliveryRepository.findMainPageCircleGraphData(type);

    return CircleGraphListDto.builder()
        .instructionData(instructionData)
        .deliveryData(deliveryData)
        .build();
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
