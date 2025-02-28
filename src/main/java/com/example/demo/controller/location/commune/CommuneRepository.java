package com.example.demo.controller.location.commune;


import com.example.demo.controller.location.district.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findByDistrict(District district);
}
