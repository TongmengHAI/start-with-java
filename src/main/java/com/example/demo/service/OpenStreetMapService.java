package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenStreetMapService {

    private final String BASE_URL = "https://nominatim.openstreetmap.org/search";

    public String searchLocation(String query) {
        String url = BASE_URL + "?q=" + query + "&format=json&addressdetails=1";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
