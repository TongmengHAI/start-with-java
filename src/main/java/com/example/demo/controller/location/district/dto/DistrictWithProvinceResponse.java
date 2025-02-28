package com.example.demo.controller.location.district.dto;

import com.example.demo.controller.location.province.Province;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictWithProvinceResponse {
    private Long id;
    private String type;
    private String khmer_type;
    private String code;
    private String name;
    private String khmer_name;

    private Province province;
}
