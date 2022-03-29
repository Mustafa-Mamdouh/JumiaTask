package com.jumia.number.validator.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponseDto {
    private String error;
    private Integer code;
}
