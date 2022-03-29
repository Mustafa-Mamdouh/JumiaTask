package com.jumia.number.validator.services;

import com.jumia.number.validator.dto.CountryNumberSchema;
import com.jumia.number.validator.properties.CountryCodeProperties;
import com.jumia.number.validator.service.interfaces.CountryLookupServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
class CountryLookupService implements CountryLookupServiceInterface {
    private final CountryCodeProperties countryCodeProperties;
    @Override
    public Map<String, CountryNumberSchema> getCountries() {
        return countryCodeProperties.getCountriesSchema();
    }
}
