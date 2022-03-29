package com.jumia.number.validator.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonPropertySourceFactory
        implements PropertySourceFactory {

    @Override
    public PropertySource createPropertySource(String name, EncodedResource resource) throws IOException {
        Map readValue = new ObjectMapper().readValue(resource.getInputStream(), Map.class);
        return new MapPropertySource("json-property", readValue);
    }
}