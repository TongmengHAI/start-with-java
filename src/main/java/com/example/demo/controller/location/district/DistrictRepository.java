package com.example.demo.controller.location.district;

import com.example.demo.controller.location.province.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByProvince(Province province);
}
