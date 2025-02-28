package com.example.demo.controller.location.district.dto;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.province.Province;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictWithCommunesResponse {
    private Long id;
    private String type;
    private String khmer_type;
    private String code;
    private String name;
    private String khmer_name;

    private List<Commune> communes;
}
