package com.example.demo.controller.location.province;

import java.sql.Timestamp;
import java.util.List;

import com.example.demo.controller.location.commune.Commune;
import com.example.demo.controller.location.district.District;
import com.example.demo.controller.location.village.Village;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Table(name = "provinces")
@Entity
@Data  // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "khmer_type")
    private String khmer_type;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "khmer_name")
    private String khmer_name;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updated_at;


//    @OneToMany
//    private List<District> districts;




}
