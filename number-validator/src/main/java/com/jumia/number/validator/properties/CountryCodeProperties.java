package com.jumia.number.validator.properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.number.validator.dto.CountryNumberSchema;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.util.Map;

@Data
@Component
public class CountryCodeProperties {
    Map<String, CountryNumberSchema> countriesSchema;

    @SneakyThrows
    @PostConstruct
    void initializeCountriesList() {
        countriesSchema = new ObjectMapper().readValue(
                new InputStreamReader(getClass().getResourceAsStream("/static/CountryRegex.json"), "UTF-8"),
                new TypeReference<Map<String, CountryNumberSchema>>() {});
    }
}
