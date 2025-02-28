package com.example.demo.controller.location.commune;

import com.example.demo.controller.location.district.District;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
