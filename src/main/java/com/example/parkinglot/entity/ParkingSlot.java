package com.example.parkinglot.entity;
import com.example.parkinglot.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(indexes = {@Index(name="idx_slot_type_status", columnList="type,status")})
public class ParkingSlot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @ManyToOne(optional=false)
    private ParkingFloor floor;

    private String slotNumber; // human readable

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    @Version
    private Long version; // optimistic locking
}
