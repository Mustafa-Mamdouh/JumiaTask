package com.jumia.number.validator.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.regex.Pattern;

@Data
public class CountryNumberSchema {
    String numberRegex;
    String countryCode;
    Pattern countryCodeRegex;

    @JsonSetter
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        countryCodeRegex = Pattern.compile(countryCode);
    }
}
