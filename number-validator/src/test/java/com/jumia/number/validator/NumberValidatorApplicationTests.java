package com.jumia.number.validator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumia.number.validator.dto.CountryNumberSchema;
import com.jumia.number.validator.dto.NumbersResponse;
import com.jumia.number.validator.enums.NumberState;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStreamReader;
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

    @BeforeAll
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @Order(1)
    void TestQueryParamPagingDefaultValues() throws Exception {
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers", null);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        assert (numbers != null);
        assert (numbers.getPageNumber() == page);
        assert (numbers.getNumberOfElements() == elements);
        assert (numbers.getData().size() == elements);
    }


    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingIdASC() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "id");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<Integer> idList = numbers.getData().stream().map(number -> number.getId()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(idList, true);
    }


    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingNameASC() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "name");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getName()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, true);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingPhoneASC() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "phone");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getPhone()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, true);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingCountryASC() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "country");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getCountry()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, true);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingStateASC() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "state");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getState().name()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, true);
    }


    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingIdDesc() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "id");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<Integer> idList = numbers.getData().stream().map(number -> number.getId()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(idList, false);
    }


    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingNameDesc() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "name");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getName()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, false);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingPhoneDesc() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "phone");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getPhone()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, false);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingCountryDesc() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "country");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getCountry()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, false);
    }

    @Test
    @Order(3)
    void TestQueryParamPagingAndSortingStateDesc() throws Exception {
        int page = 0;
        int elements = 100;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("page", page);
        paramMap.put("numberOfItems", elements);
        paramMap.put("sort", "state");
        paramMap.put("direction", "DESC");
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        List<String> nameList = numbers.getData().stream().map(number -> number.getState().name()).collect(Collectors.toList());
        assert TestUtils.isListOrdered(nameList, false);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
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
        NumbersResponse numbers = TestUtils.compareTwoImplementationAndReturnDbOne(mockMvc, "/numbers",
                paramMap);
        assert numbers.getData().stream().allMatch(number -> NumberState.NOT_VALID.equals(number.getState()));
    }

    @Test
    @Order(9)
    void TestCountryLookUp() throws Exception {
        String result = TestUtils.performGetRequest(mockMvc, "/Countries", null, String.class);
        Map<String, CountryNumberSchema> countriesSchema = new ObjectMapper().readValue(
                new InputStreamReader(getClass().getResourceAsStream("/static/CountryRegex.json"), "UTF-8"),
                new TypeReference<Map<String, CountryNumberSchema>>() {
                });

        assert countriesSchema.size() > 0;
    }
}
