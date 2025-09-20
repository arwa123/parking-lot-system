package com.example.parkinglot.service.strategy;
import com.example.parkinglot.entity.ParkingSlot;
import com.example.parkinglot.enums.VehicleType;
import java.util.Optional;

public interface SlotAllocationStrategy {
    Optional<ParkingSlot> allocate(VehicleType type);
    String name();
}
