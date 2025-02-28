package com.example.demo.controller.location.district;


import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.province.Province;
import com.example.demo.controller.location.village.Village;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "districts")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String khmer_type;

    private String code;

    private String name;
    private String khmer_name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id")
    @JsonIgnore
    private Province province;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updated_at;


//    @OneToMany
//    private List<Commune> communes;

}
