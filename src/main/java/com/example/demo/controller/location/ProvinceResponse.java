package com.example.demo.controller.location;

import lombok.Builder;

@Builder
public record ProvinceResponse(
        Long id,
        String name
) {
}
