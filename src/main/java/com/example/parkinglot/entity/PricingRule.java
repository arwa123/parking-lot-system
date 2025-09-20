package com.example.parkinglot.entity;
import com.example.parkinglot.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "type"))
public class PricingRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private int freeMinutes;
    private BigDecimal hourlyRate;
}
