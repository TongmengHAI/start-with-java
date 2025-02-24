package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotFoundController {

    @GetMapping("/notFound")
    public String hello() {
        return "page not Found From Spring Boot!".toString();
    }
}
