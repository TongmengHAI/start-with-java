package com.example.demo.controller.location.province;

import com.example.demo.controller.location.district.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Long> {

    void deleteByName(String name);

}
