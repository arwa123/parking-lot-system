package com.example.parkinglot.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParkingLot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingFloor> floors = new ArrayList<>();
}
