package com.jumia.number.validator.controllers;

import com.jumia.number.validator.service.interfaces.CountryLookupServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryLookupController {
    private final CountryLookupServiceInterface countryLookupServiceInterface;
    @GetMapping
    public ResponseEntity getAllPhonesServerSideImpl() {
        return ResponseEntity.ok(countryLookupServiceInterface.getCountries());
    }

}
