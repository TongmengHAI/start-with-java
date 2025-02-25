package com.example.demo.controller.location;

import java.sql.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "provinces")
@Entity
@Data  // Includes @Getter, @Setter, @ToString, @EqualsAndHashCode
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class ProvinceEntity {
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

    private Timestamp created_at = null;
    private Timestamp updated_at = null;

}
