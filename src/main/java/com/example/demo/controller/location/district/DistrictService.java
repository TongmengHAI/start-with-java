package com.example.demo.controller.location.district;

import com.example.demo.controller.location.district.dto.DistrictWithProvinceResponse;
import com.example.demo.controller.location.province.Province;
import com.example.demo.controller.location.province.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    public List<District> getDistrict(Long provinceId){

        Province province = provinceRepository.findById(provinceId).orElseThrow(() -> new RuntimeException("Province not found"));

        return districtRepository.findByProvince(province);
    }

    public List<DistrictWithProvinceResponse> getDistrictByProvince(Long provinceId){

        Province province = provinceRepository.findById(provinceId).orElseThrow(() -> new RuntimeException("Province not found"));

        List<District> districts = districtRepository.findByProvince(province);
        List<DistrictWithProvinceResponse> districtResponse = districts.stream()
                .map(item -> new DistrictWithProvinceResponse(
                        item.getId(),
                        item.getType(),
                        item.getKhmer_type(),
                        item.getCode(),
                        item.getName(),
                        item.getKhmer_name(),
                        province
                )).collect(Collectors.toList());

        return districtResponse;
    }

    public District getDistrictById(Long id){
        return districtRepository.findById(id).orElseThrow(() -> new RuntimeException("District not found"));
    }


}
