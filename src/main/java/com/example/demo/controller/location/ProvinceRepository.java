package com.example.demo.controller.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {

    void deleteByName(String name);
}
