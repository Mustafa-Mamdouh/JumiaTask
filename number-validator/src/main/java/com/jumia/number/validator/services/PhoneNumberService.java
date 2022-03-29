package com.jumia.number.validator.services;

import com.jumia.number.validator.constants.ErrorMessages;
import com.jumia.number.validator.dto.CustomerNumberDto;
import com.jumia.number.validator.dto.NumbersResponse;
import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.enums.NumberState;
import com.jumia.number.validator.exceptions.ApplicationException;
import com.jumia.number.validator.models.Customer;
import com.jumia.number.validator.properties.CountryCodeProperties;
import com.jumia.number.validator.repos.CustomerRepository;
import com.jumia.number.validator.service.interfaces.PhoneNumberServiceInterface;
import com.jumia.number.validator.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PhoneNumberService implements PhoneNumberServiceInterface {
    private final CustomerRepository customerRepository;
    private final NumberValidationService numberValidationService;
    private final ModelMapper mapper;
    private final CountryCodeProperties countryCodeProperties;


    public NumbersResponse getNumbersList(SearchCriteria criteria) {
        return mapToResponse(applySearchCriteria(customerRepository.findAll(), criteria), criteria);
    }

    private List<Customer> applySearchCriteria(List<Customer> customerList, SearchCriteria criteria) {
        return customerList.stream()
                .peek(this::updateCountry).peek(numberValidationService::updateState)
                .filter(byCountry(criteria.getCountry())).filter(byState(criteria.getState())).collect(Collectors.toList());

    }

    private NumbersResponse mapToResponse(List<Customer> customerList, SearchCriteria criteria) {
        List<CustomerNumberDto> numbersDto = customerList.stream()
                .map(customer -> mapper.map(customer, CustomerNumberDto.class)).collect(Collectors.toList());
        Pageable pageable = criteria.asPageable();
        return new NumbersResponse(new PageImpl<>(ListUtils.getSubList(numbersDto, (int) pageable.getOffset(), pageable.getPageSize()), pageable, numbersDto.size()));
    }


    private Predicate<? super Customer> byCountry(String country) {
        return customer -> {
            if (StringUtils.isEmpty(country)) {
                return true;
            } else
                return country.equalsIgnoreCase(customer.getCountry());
        };
    }

    private Predicate<? super Customer> byState(NumberState state) {
        return customer -> {
            if (state == null) {
                return true;
            } else
                return state.equals(customer.getState());
        };
    }

    public void updateCountry(Customer number) {
        countryCodeProperties.getCountriesSchema().entrySet().forEach(entry -> {
            if (entry.getValue().getCountryCodeRegex().matcher(number.getPhone()).find()) {
                number.setCountry(entry.getKey());
                return;
            }
        });

        if (StringUtils.isEmpty(number.getCountry())) {
            throw new ApplicationException(ErrorMessages.COUNTRY_NOT_FOUND.getStatus().value(), ErrorMessages.NUMBER_DOES_NOT_BELONG_TO_ANY_SUPPORTED_COUNTRY.setData(number.getPhone()));
        }
    }
}
