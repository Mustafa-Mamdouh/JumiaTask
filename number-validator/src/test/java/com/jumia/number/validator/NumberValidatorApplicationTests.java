package com.jumia.number.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jumia.number.validator.dto.NumbersResponse;
import com.jumia.number.validator.enums.NumberState;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NumberValidatorApplicationTests {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeAll
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Order(1)
    void TestQueryParamPagingDefaultValues() throws Exception {
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                null, NumbersResponse.class);
        assert (numbers != null);
        assert (numbers.getPageNumber() == 0);
        assert (numbers.getNumberOfElements() == 10);
    }

    @Test
    @Order(2)
    void TestQueryParamPaging() throws Exception {
        int page = 2;
        int elements = 5;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert (numbers != null);
        assert (numbers.getPageNumber() == page);
        assert (numbers.getNumberOfElements() == elements);
        assert (numbers.getData().size() == elements);
    }


    @Test
    @Order(3)
    void TestQueryParamPagingAndSorting() throws Exception {
        int page = 0;
        int elements = 10;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "id");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert (numbers.getData().size() == elements);
        List<Integer> idList = numbers.getData().stream().map(number -> number.getId()).collect(Collectors.toList());
        assert (idList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()).equals(idList));
    }

    @Test
    @Order(4)
    void TestGetByExistingCountry() throws Exception {
        int page = 0;
        int elements = 1000;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        String morocco = "Morocco";
        paramMap.put("country", morocco);
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert numbers.getNumberOfElements() > 0;
        assert numbers.getData().stream().allMatch(number -> "Morocco".equalsIgnoreCase(number.getCountry()));
    }

    @Test
    @Order(5)
    void TestGetByNoneExistingCountry() throws Exception {
        int page = 0;
        int elements = 1000;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        String morocco = "NotExistMorocco";
        paramMap.put("country", morocco);
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert numbers.getNumberOfElements() == 0;
        assert numbers.getData().isEmpty();
    }

    @Test
    @Order(6)
    void TestStatesWithGetAll() throws Exception {
        int page = 0;
        int elements = 1000;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert numbers.getData().stream().anyMatch(number -> NumberState.NOT_VALID.equals(number.getState()))
                && numbers.getData().stream().anyMatch(number -> NumberState.VALID.equals(number.getState()));
    }

    @Test
    @Order(7)
    void TestGetByValidState() throws Exception {
        int page = 0;
        int elements = 1000;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("state", "VALID");
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert numbers.getData().stream().allMatch(number -> NumberState.VALID.equals(number.getState()));
    }

    @Test
    @Order(8)
    void TestGetByNotValid() throws Exception {
        int page = 0;
        int elements = 1000;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("state", "NOT_VALID");
        NumbersResponse numbers = TestUtils.performGetRequest(mockMvc,  "/numbers",
                paramMap, NumbersResponse.class);
        assert numbers.getData().stream().allMatch(number -> NumberState.NOT_VALID.equals(number.getState()));
    }
}
