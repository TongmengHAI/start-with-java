package com.example.demo.controller.location.commune;

import com.example.demo.controller.location.commune.dto.CommuneWithVillagesRespose;
import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.district.dto.DistrictWithCommunesResponse;
import com.example.demo.controller.location.village.Village;
import com.example.demo.controller.location.village.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class CommuneController {
    @Autowired
    private VillageService villageService;

    @Autowired
    private CommuneService communeService;


    @GetMapping("/commune/{id}/villages")
    public CommuneWithVillagesRespose getCommunesByDistrict(@PathVariable Long id) {
        Commune commune = communeService.getCommuneById(id);

        List<Village> villages = villageService.getVillagesByCommuneId(commune.getId());

        CommuneWithVillagesRespose districtWithCommunesResponse = new CommuneWithVillagesRespose(
                commune.getId(),
                commune.getType(),
                commune.getKhmer_type(),
                commune.getName(),
                commune.getKhmer_name(),
                commune.getCode(),

                villages

        );
        return districtWithCommunesResponse;
    }
}
