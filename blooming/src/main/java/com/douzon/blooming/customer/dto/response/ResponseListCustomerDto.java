package com.douzon.blooming.customer.dto.response;

import com.douzon.blooming.PageDto;
import com.douzon.blooming.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ResponseListCustomerDto extends PageDto<ResponseCustomerDto> {

    public ResponseListCustomerDto(List<ResponseCustomerDto> list, Integer currentPage, boolean hasNextPage, boolean hasPreviousPage) {
        super(list, currentPage, hasNextPage, hasPreviousPage);
    }
}
