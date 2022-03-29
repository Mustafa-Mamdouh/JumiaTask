package com.jumia.number.validator.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -184305369725661529L;
    private Integer responseCode;
    private String reason;

    public ApplicationException(Integer responseCode, String reason) {
        this.responseCode = responseCode;
        this.reason = reason;
    }

}

