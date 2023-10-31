package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.instruction.dto.response.GetInstructionDto;

import java.util.List;

public class RequestDeliveryDto {
    private List<GetInstructionDto> instructions;
    private String customerNo;
    private String deliveryDate;

}
