package com.jumia.number.validator.services;

import com.jumia.number.validator.dto.CustomerNumberDto;
import com.jumia.number.validator.dto.NumbersResponse;
import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.models.Customer;
import com.jumia.number.validator.repos.CustomerRepository;
import com.jumia.number.validator.service.interfaces.PhoneNumberServiceInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service()
@RequiredArgsConstructor
class DbPhoneNumberService implements PhoneNumberServiceInterface {
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    public NumbersResponse getNumbersList(SearchCriteria criteria) {
        return mapToResponse(customerRepository.findAllCategorized(criteria));
    }

    private NumbersResponse mapToResponse(Page<Customer> customerPage) {
        List<CustomerNumberDto> numbersDto = customerPage.getContent().stream()
                .map(customer -> mapper.map(customer, CustomerNumberDto.class)).collect(Collectors.toList());
        return new NumbersResponse(customerPage, numbersDto);
    }
}
