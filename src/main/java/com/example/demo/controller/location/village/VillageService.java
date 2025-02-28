package com.example.demo.controller.location.village;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.commune.CommuneService;
import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.district.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageService {
    @Autowired
    private VillageRepository villageRepository;
    @Autowired
    private CommuneService communeService;

    public List<Village> getVillagesByCommuneId(Long id){
        Commune commune = communeService.getCommuneById(id);
        List<Village> villages = villageRepository.findByCommune(commune);
        return villages;
    }
}
