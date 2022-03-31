package com.jumia.number.validator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index() {
        return "index.html";
    }
}

