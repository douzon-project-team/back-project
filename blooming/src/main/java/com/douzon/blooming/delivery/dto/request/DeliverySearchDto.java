package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliverySearchDto {
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 8;

    private DeliveryStatus deliveryStatus;
    private String employeeName;
    private String startDate;
    private String endDate;
    @Setter
    private Integer page = DEFAULT_PAGE;
    @Setter
    private Integer pageSize = DEFAULT_PAGE_SIZE;
}
