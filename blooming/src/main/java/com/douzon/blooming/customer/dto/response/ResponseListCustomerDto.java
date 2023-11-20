package com.douzon.blooming.customer.dto.response;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListCustomerDto {
    private List<ResponseCustomerDto> list;
    private Integer currentPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
}
