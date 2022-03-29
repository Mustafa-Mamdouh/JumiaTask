package com.jumia.number.validator.controllers;

import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.service.interfaces.PhoneNumberServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PhoneNumberController {
    private final PhoneNumberServiceInterface dbPhoneNumberService;
    private final PhoneNumberServiceInterface phoneNumberService;


    @GetMapping
    public ResponseEntity getAllPhones(@Valid SearchCriteria searchCriteria) {
        return ResponseEntity.ok(dbPhoneNumberService.getNumbersList(searchCriteria));
    }

    @GetMapping("/server")
    public ResponseEntity getAllPhonesServerSideImpl(@Valid SearchCriteria searchCriteria) {
        return ResponseEntity.ok(phoneNumberService.getNumbersList(searchCriteria));
    }
}
