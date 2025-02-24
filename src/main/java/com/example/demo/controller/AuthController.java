package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.LoginModel;

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginModel request) {
        Map<String, String> response = new HashMap<>();

        // Debugging to check received request
        System.out.println("Received Username: " + request.getUsername());
        System.out.println("Received Password: " + request.getPassword());

        response.put("message", "Login successful!");
        response.put("data", request.getUsername() + " " + request.getPassword());

        return ResponseEntity.ok(response);
    }

}
