package com.jumia.number.validator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumbersResponse {
    Long total;
    int pageNumber;
    int numberOfElements;
    int pageSize;
    List<? extends CustomerNumberDto> data;

    public NumbersResponse(Page page) {
        this(page, page.getContent());
    }

    public NumbersResponse(Page page, List mappedContent) {
        total = page.getTotalElements();
        pageNumber = page.getNumber();
        numberOfElements = page.getNumberOfElements();
        pageSize = page.getSize();
        data = mappedContent;
    }
}
