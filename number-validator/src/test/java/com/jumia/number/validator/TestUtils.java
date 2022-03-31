package com.jumia.number.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class TestUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static  <T> T performGetRequest(MockMvc mockMvc, String url, Map<String, Object> queryParams, Class<T> returnType) throws Exception {
        MockHttpServletRequestBuilder getRequest = get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        if (queryParams != null) {
            queryParams.forEach((key, value) -> {
                getRequest.param(key, String.valueOf(value));
            });
        }
        MvcResult mvcResult = mockMvc.perform(getRequest).andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), returnType);
    }
}
