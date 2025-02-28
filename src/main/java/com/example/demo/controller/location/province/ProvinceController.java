package com.example.demo.controller.location.province;

import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.district.DistrictService;
import com.example.demo.controller.location.province.dto.ProvinceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/location")
public class ProvinceController {

    @Autowired
    private ProviceService provinceService;
    @Autowired
    private DistrictService districtService;



    @GetMapping("/provinces/test")
    public List<ProvinceResponse1> getProvinces() {
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

        Optional<Province> province = provinceService.getProvinceById(1L);

        List<ProvinceResponse1> provinceResponses1 = province.isPresent()
                ? List.of(ProvinceResponse1.builder()
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

//        provinceService.updateUser(35L, "Banteay ");

        return provinceResponses1;
    }

    @GetMapping("/provinces")
    public List<ProvinceResponse1> getProvinces2() {
        return provinceService.getAllProvinces();
    }



    //    province with districts
    @GetMapping("/province/{id}")
    public ProvinceResponse getProvinceWithDistricts(@PathVariable Long id) {
        Province province = provinceService.getProvinceById(id).orElseThrow(() -> new RuntimeException("Province not found"));

        List<District> districts = districtService.getDistrict(province.getId());


        ProvinceResponse provinceResponse = new ProvinceResponse(
                province.getId(),
                province.getType(),
                province.getKhmer_type(),
                province.getCode(),
                province.getName(),
                province.getKhmer_name(),

                districts
        );
        return provinceResponse;
    }



}
