package com.example.parkinglot.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParkingFloor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int level;

    @ManyToOne(optional=false)
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSlot> slots = new ArrayList<>();
}
