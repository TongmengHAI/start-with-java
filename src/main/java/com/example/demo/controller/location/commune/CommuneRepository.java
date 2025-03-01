package com.example.demo.controller.location.commune;


import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.village.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findByDistrict(District district);

    @Transactional
    @Procedure(name = "getCommuneWithVillages")
    List<Object[]> getCommuneWithVillages(@Param("c_id") int c_id);
}
