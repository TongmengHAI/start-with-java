package com.example.demo.controller.location.commune;


import com.example.demo.controller.location.district.District;
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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "communes")
public class Commune {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;
    @Column(name = "khmer_type")
    private String khmer_type;

    @Column(name = "name")
    private String name;
    @Column(name = "khmer_name")
    private String khmer_name;

    @Column(name = "code")
    private String code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id")
    @JsonIgnore
    private Province province;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private java.sql.Timestamp created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updated_at;


//    @OneToMany
//    private List<Village> villages;

}
