package com.example.demo.controller.location.commune;

import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.village.Village;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class CommuneService {

    @Autowired
    private CommuneRepository communeRepository;

    public Commune getCommuneById(Long id){
        return communeRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Commune> getCommuneByDistrict(District district){

        List communes = communeRepository.findByDistrict(district);


        return communes;

    }

    @Transactional
    public List<Village> getCommuneWithVillages(Long id){  // get data by call store procedure process
        List<Object[]> rawVillages = communeRepository.getCommuneWithVillages(id.intValue());

        for (Object[] row : rawVillages) {
            System.out.println("Row: " + Arrays.toString(row));
        }

        // need to define Custom Constructor manually for Procedure result (Object[])
        List<Village> villages = rawVillages.stream().map(obj -> new Village(
                ((Number) obj[0]).longValue(),  // ID
                (String) obj[1],               // Type
                (String) obj[2],               // Khmer Type
                (String) obj[5],               // Name
                (String) obj[4],               // Khmer Name
                (String) obj[3]                // Code
        )).toList();

        return villages;
    }


}
