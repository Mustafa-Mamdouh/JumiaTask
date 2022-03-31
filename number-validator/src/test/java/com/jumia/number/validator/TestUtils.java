package com.jumia.number.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.number.validator.dto.NumbersResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class TestUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T performGetRequest(MockMvc mockMvc, String url, Map<String, Object> queryParams, Class<T> returnType) throws Exception {
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

    public static NumbersResponse compareTwoImplementationAndReturnDbOne(MockMvc mockMvc, String url, Map<String, Object> queryParams) throws Exception {
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc, "/numbers",
                queryParams, NumbersResponse.class);
        NumbersResponse numberInMemorey = TestUtils.performGetRequest(mockMvc, "/numbers/server",
                queryParams, NumbersResponse.class);
        assert Objects.equals(numberInMemorey, numbers);
        return numbers;
    }

    public static boolean isListOrdered(List idList, boolean isAsc) {
        if (isAsc) {
            return idList.stream().sorted().collect(Collectors.toList()).equals(idList);
        } else {
            return idList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()).equals(idList);
        }
    }

}
