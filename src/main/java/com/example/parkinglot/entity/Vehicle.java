package com.example.parkinglot.entity;
import com.example.parkinglot.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "plateNo"))
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plateNo;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String ownerId; // subject / sub claim from OAuth
}
