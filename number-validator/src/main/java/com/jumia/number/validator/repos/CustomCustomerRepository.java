package com.jumia.number.validator.repos;

import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.models.Customer;
import org.springframework.data.domain.Page;

public interface CustomCustomerRepository {
    Page<Customer> findAllCategorized(SearchCriteria page);
}
