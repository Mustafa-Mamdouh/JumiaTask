package com.jumia.number.validator.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MappingConfig {
    @Bean
    ModelMapper getSimpleMapper() {
        return new ModelMapper();
    }
}
