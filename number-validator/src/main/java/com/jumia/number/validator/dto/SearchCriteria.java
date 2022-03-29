package com.jumia.number.validator.dto;

import com.jumia.number.validator.enums.NumberState;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchCriteria extends PagingAndSortingCriteria {
    String country;
    NumberState state;

    public String asCondition() {
        List<String> andConditions = new ArrayList<>();
        if (!StringUtils.isEmpty(country)) {
            andConditions.add(String.format(" lower(country) = '%s'", country.toLowerCase()));
        }
        if (state != null) {
            andConditions.add(String.format(" lower(state) = '%s'", state.name().toLowerCase()));
        }
        if (!andConditions.isEmpty()) {
            return String.format(" Where %s", String.join(" and ", andConditions));
        }
        return "";
    }
}
