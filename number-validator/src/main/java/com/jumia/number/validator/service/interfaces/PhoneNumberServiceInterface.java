package com.jumia.number.validator.service.interfaces;

import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.dto.NumbersResponse;

public interface PhoneNumberServiceInterface {
    NumbersResponse getNumbersList(SearchCriteria criteria);
}
