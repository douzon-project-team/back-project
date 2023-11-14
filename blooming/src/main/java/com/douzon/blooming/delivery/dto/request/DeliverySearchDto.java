package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliverySearchDto {
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 8;

    private DeliveryStatus progressStatus;
    private String employeeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Setter
    private Integer page = DEFAULT_PAGE;
    @Setter
    private Integer pageSize = DEFAULT_PAGE_SIZE;
}
