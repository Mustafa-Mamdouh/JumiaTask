package com.jumia.number.validator.services;

import com.jumia.number.validator.models.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class NumberSortingService {
    @Bean("idASC")
    private Comparator<? super Customer> byId() {
        return (a, b) -> Integer.compare(a.getId(), b.getId());
    }

    @Bean("idDESC")
    private Comparator<? super Customer> byIdDesc() {
        return (b, a) -> Integer.compare(a.getId(), b.getId());
    }

    @Bean("phoneASC")
    private Comparator<? super Customer> byPhone() {
        return (a, b) -> a.getPhone().compareTo(b.getPhone());
    }

    @Bean("phoneDESC")
    private Comparator<? super Customer> byPhoneDesc() {
        return (b, a) -> a.getPhone().compareTo(b.getPhone());
    }

    @Bean("nameASC")
    private Comparator<? super Customer> byName() {
        return (a, b) -> a.getName().compareTo(b.getName());
    }

    @Bean("nameDESC")
    private Comparator<? super Customer> byNameDesc() {
        return (b, a) -> a.getName().compareTo(b.getName());
    }

    @Bean("stateASC")
    private Comparator<? super Customer> byState() {
        return (a, b) -> a.getState().name().compareTo(b.getState().name());
    }

    @Bean("stateDESC")
    private Comparator<? super Customer> byStateDesc() {
        return (b, a) -> a.getState().name().compareTo(b.getState().name());
    }

    @Bean("countryASC")
    private Comparator<? super Customer> byCountry() {
        return (a, b) -> a.getCountry().compareTo(b.getCountry());
    }

    @Bean("countryDESC")
    private Comparator<? super Customer> byCountryDesc() {
        return (b, a) -> a.getCountry().compareTo(b.getCountry());
    }

}
