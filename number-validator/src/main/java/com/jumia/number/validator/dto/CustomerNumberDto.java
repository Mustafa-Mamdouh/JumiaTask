package com.jumia.number.validator.dto;

import com.jumia.number.validator.enums.NumberState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNumberDto  {
    Integer id;
    String name;
    String phone;
    String country;
    NumberState state;
}
