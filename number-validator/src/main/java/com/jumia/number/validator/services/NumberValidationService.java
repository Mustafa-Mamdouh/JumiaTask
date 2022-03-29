package com.jumia.number.validator.services;

import com.jumia.number.validator.constants.ErrorMessages;
import com.jumia.number.validator.dto.CountryNumberSchema;
import com.jumia.number.validator.enums.NumberState;
import com.jumia.number.validator.exceptions.ApplicationException;
import com.jumia.number.validator.models.Customer;
import com.jumia.number.validator.properties.CountryCodeProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NumberValidationService {
    private final CountryCodeProperties countryCodeProperties;

    public void updateState(Customer number) {
        CountryNumberSchema countryNumberSchema = countryCodeProperties.getCountriesSchema().get(number.getCountry());
        if (countryNumberSchema == null) {
            throw new ApplicationException(ErrorMessages.COUNTRY_NOT_FOUND.getStatus().value(), ErrorMessages.COUNTRY_NOT_FOUND.setData(number.getCountry()));
        }
        boolean matches = number.getPhone().matches(countryNumberSchema.getNumberRegex());
        number.setState(matches ? NumberState.VALID : NumberState.NOT_VALID);
    }
}
