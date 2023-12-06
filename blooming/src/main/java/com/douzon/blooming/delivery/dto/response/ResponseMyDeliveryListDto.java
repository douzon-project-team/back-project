package com.douzon.blooming.delivery.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseMyDeliveryListDto {
    private List<ResponseMyDeliveryDto> list;
}
