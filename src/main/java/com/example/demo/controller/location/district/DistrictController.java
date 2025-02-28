package com.example.demo.controller.location.district;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.commune.CommuneService;
import com.example.demo.controller.location.district.dto.DistrictWithCommunesResponse;
import com.example.demo.controller.location.district.dto.DistrictWithProvinceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class DistrictController {

    @Autowired
    private DistrictService districtService;
    @Autowired
    private CommuneService communeService;

    //    districts with province
    @GetMapping("/district/{id}")
    public List<DistrictWithProvinceResponse> getDistrictsByProvince(@PathVariable Long id) {
        return districtService.getDistrictByProvince(id);
    }

    //    districts with province
    @GetMapping("/district/{id}/communes")
    public DistrictWithCommunesResponse getCommunesByDistrict(@PathVariable Long id) {
        District district = districtService.getDistrictById(id);

        List<Commune> communes = communeService.getCommuneByDistrict(district);

        DistrictWithCommunesResponse districtWithCommunesResponse = new DistrictWithCommunesResponse(
                district.getId(),
                district.getType(),
                district.getKhmer_type(),
                district.getCode(),
                district.getName(),
                district.getKhmer_name(),

                communes
        );

        return districtWithCommunesResponse;
    }

}
