package com.example.demo.controller.location.province;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ProviceService {

    @Autowired
    private ProvinceRepository provinceRepository;


    public Optional<Province> getProvinceById(Long id) {

        return provinceRepository.findById(id);
    }

    public Province saveProvince(Province provinceEntity) {
        return provinceRepository.save(provinceEntity);
    }

    public List<ProvinceResponse1> getAllProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        if (provinces != null) {
            return provinces.stream().map(
                    item -> ProvinceResponse1.builder()
                            .id(item.getId())
                            .name(item.getName())
                            .build()
            ).toList();
        }

        return null;

    }

    @Transactional
    public void deleteProvinceByName(String name) {
        provinceRepository.deleteByName(name);
    }

    @Transactional
    public void createOrUpdate(Long id) {
//        Province province = provinceRepository.findById(id).isEmpty() ? new Province() : provinceRepository.findById(id).get();
//        province.setName(newName);
//        province.setCode("1");
//        province.setType("province");
//        province.setKhmer_type("ខេត្ត");
//        province.setKhmer_name("បន្ទាយមានជ័យ");
//
//        provinceRepository.save(province);

//        System.out.println(province);

    }




}
