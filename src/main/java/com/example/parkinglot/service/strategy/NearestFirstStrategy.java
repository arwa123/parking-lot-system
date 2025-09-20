package com.example.parkinglot.service.strategy;
import com.example.parkinglot.entity.ParkingSlot;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.ParkingSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NearestFirstStrategy implements SlotAllocationStrategy {
    private final ParkingSlotRepository slotRepository;

    @Override
    public Optional<ParkingSlot> allocate(VehicleType type) {
        return slotRepository.findNearestFreeSlotForUpdate(type).stream().findFirst();
    }

    @Override
    public String name() { return "NEAREST_FIRST"; }
}
