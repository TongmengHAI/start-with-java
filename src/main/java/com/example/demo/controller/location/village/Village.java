package com.example.demo.controller.location.village;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.province.Province;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.beans.ConstructorProperties;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "villages")
public class Village {

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
    @JoinColumn(name = "commune_id")
    @JsonIgnore
    private Commune commune;

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
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updated_at;


    // 👇 Custom constructor for stored procedure mapping
    @ConstructorProperties({"id", "type", "khmer_type", "name", "khmer_name", "code"})
    public Village(Long id, String type, String khmer_type, String name, String khmer_name, String code) {
        this.id = id;
        this.type = type;
        this.khmer_type = khmer_type;
        this.name = name;
        this.khmer_name = khmer_name;
        this.code = code;
    }



}
