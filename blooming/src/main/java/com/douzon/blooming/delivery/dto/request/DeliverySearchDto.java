package com.douzon.blooming.delivery.dto.request;

import com.douzon.blooming.SearchDto;
import com.douzon.blooming.auth.EmployeeRole;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@ToString
public class DeliverySearchDto extends SearchDto {

    private final DeliveryStatus progressStatus;
    private String employeeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public DeliverySearchDto(DeliveryStatus progressStatus, String employeeName,
                             LocalDate startDate, LocalDate endDate, Integer pageSize,
                             Integer page) {
        super(pageSize, page);
        this.progressStatus = progressStatus;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}