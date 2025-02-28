package com.example.demo.controller.location.province.dto;


import com.example.demo.controller.location.district.District;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProvinceResponse {
    private Long id;
    private String type;
    private String khmer_type;
    private String code;
    private String name;
    private String khmer_name;

    private List<District> districts;

}
