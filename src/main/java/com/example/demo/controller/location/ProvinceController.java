package com.example.demo.controller.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class ProvinceController {

    @Autowired
    private ProviceService provinceService;


    @GetMapping("/provinces/test")
    public List<ProvinceResponse> getProvinces() {
//        List<ProvinceEntity> provinces = provinceRepository.findAll();
//        ProvinceEntity province = provinceRepository.findByName("Kandal");
//
//        ProvinceEntity province2 = provinceRepository.deleteByName("Kep");
//
//        List<ProvinceEntity> provinces = province == null ? List.of() : List.of(province);


//
//        List<ProvinceResponse> provinceResponses = provinces.stream().map(
//                item -> ProvinceResponse.builder()
//                        .id(item.getId())
//                        .name(item.getName())
//                        .build();
//        ).toList();
//
//        List<ProvinceResponse> provinceResponses = provinces.stream().map(
//                item -> ProvinceResponse.builder()
//                        .id(item.getId())
//                        .name(item.getName())
//                        .build()
//        ).toList();


//        for(ProvinceEntity p: provinces){
//           ProvinceResponse provinceResponse = new ProvinceResponse(province.ge)
//            System.out.println(p.getId());
//        }

        Optional<ProvinceEntity> province = provinceService.getProvinceById(1L);

        List<ProvinceResponse> provinceResponses = province.isPresent()
                ? List.of(ProvinceResponse.builder()
                        .id(province.get().getId())
                        .name(province.get().getName())
                        .build())
                : List.of();


//        if (
//                provinceService.getProvinceById(26L).isPresent()
//        ){
//            provinceService.deleteProvinceByName("Kep");
//            return null;
//
//        }

        provinceService.updateUser(35L, "Banteay ");

        return provinceResponses;
    }

    @GetMapping("/provinces")
    public List<ProvinceResponse> getProvinces2() {
        return provinceService.getAllProvinces();
    }

    @PostMapping("/provinces/{id}/update")
    public ProvinceResponse updateProvince(@PathVariable Long id, ProvinceEntity provinceEntity) {


        return null;
    }

}
