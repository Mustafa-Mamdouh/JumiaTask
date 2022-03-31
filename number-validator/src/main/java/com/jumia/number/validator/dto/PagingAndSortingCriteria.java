package com.jumia.number.validator.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;

@Data
public class PagingAndSortingCriteria {
    @Min(10)
    int numberOfItems = 10;
    @Min(0)
    int page = 0;
    String sort = "id";
    Sort.Direction direction = Sort.Direction.ASC;
    public Pageable asPageable() {
        if (!StringUtils.isEmpty(sort)) {
            return PageRequest.of(page, numberOfItems, direction, sort.split(","));
        }else{
            return PageRequest.of(page, numberOfItems);
        }
    }
}
