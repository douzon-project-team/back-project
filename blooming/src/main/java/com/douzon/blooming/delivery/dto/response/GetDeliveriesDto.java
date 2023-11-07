package com.douzon.blooming.delivery.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetDeliveriesDto {
    private List<ListDeliveryDto> deliveries;
    private Integer currentPage;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
}
