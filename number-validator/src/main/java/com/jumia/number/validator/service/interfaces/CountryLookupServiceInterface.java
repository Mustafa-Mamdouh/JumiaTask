package com.jumia.number.validator.service.interfaces;

import com.jumia.number.validator.dto.CountryNumberSchema;

import java.util.Map;

public interface CountryLookupServiceInterface {
    Map<String, CountryNumberSchema> getCountries();
}
