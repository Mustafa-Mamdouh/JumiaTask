package com.jumia.number.validator.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    COUNTRY_NOT_FOUND(HttpStatus.BAD_REQUEST, "Can't found country: %s."),
    NUMBER_DOES_NOT_BELONG_TO_ANY_SUPPORTED_COUNTRY(HttpStatus.BAD_REQUEST, "Number: %s doesn't belong to any supported country!");

    private HttpStatus status;
    private String message;

    public String setData(Object... object) {
        return String.format(message, object);
    }
}
