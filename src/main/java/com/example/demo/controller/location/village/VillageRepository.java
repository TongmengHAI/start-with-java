package com.example.demo.controller.location.village;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.district.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageRepository extends JpaRepository<Village, Long> {

    List<Village> findByCommune(Commune commune);
}
