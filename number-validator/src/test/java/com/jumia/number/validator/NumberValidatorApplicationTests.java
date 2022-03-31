package com.jumia.number.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jumia.number.validator.dto.NumbersResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
		MvcResult mvcResult = this.mockMvc.perform(get("/numbers")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		NumbersResponse numbers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), NumbersResponse.class);
		assert (numbers != null);
		assert (numbers.getPageNumber() == 0);
		assert (numbers.getNumberOfElements() == 10);
	}

	@Test
	@Order(2)
	void TestQueryParamPaging() throws Exception {
		int page = 2;
		int elements = 5;
		MvcResult mvcResult = this.mockMvc.perform(get("/numbers")
				.param("page",String.valueOf(page)).param("numberOfItems",String.valueOf(elements))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		NumbersResponse numbers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), NumbersResponse.class);
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
		MvcResult mvcResult = this.mockMvc.perform(get("/numbers")
				.param("page",String.valueOf(page)).param("numberOfItems",String.valueOf(elements))
				.param("sort","id").param("direction","DESC")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		NumbersResponse numbers = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), NumbersResponse.class);
		assert (numbers.getData().size() == elements);
		List<Integer> idList = numbers.getData().stream().map(number -> number.getId()).collect(Collectors.toList());
		assert(idList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()).equals(idList));

	}

}
