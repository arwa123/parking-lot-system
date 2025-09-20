package com.example.parkinglot.entity;
import com.example.parkinglot.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(indexes = {@Index(name="idx_ticket_vehicle_active", columnList="vehicle_id,status")})
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    private Vehicle vehicle;

    @ManyToOne(optional=false)
    private ParkingSlot slot;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
