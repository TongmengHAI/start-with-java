package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.OpenStreetMapService;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private OpenStreetMapService openStreetMapService;

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return openStreetMapService.searchLocation(query);
    }
}
