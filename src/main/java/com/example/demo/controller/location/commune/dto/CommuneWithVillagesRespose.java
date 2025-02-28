package com.example.demo.controller.location.commune.dto;

import com.example.demo.controller.location.village.Village;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommuneWithVillagesRespose {

    private Long id;
    private String type;
    private String khmer_type;

    private String name;
    private String khmer_name;

    private String code;

    List<Village> villages;


}
