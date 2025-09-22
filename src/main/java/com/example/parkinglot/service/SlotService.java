package com.example.parkinglot.service;

import com.example.parkinglot.dto.SlotResponse;
import com.example.parkinglot.entity.ParkingFloor;
import com.example.parkinglot.entity.ParkingSlot;
import com.example.parkinglot.enums.SlotStatus;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.ParkingFloorRepository;
import com.example.parkinglot.repository.ParkingSlotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final ParkingSlotRepository slotRepo;
    private final ParkingFloorRepository floorRepo;

    @Transactional
    public SlotResponse addSlot(Long floorId, VehicleType type, String slotNumber) {
        ParkingFloor floor = floorRepo.findById(floorId)
                .orElseThrow(() -> new EntityNotFoundException("Floor not found: " + floorId));

        ParkingSlot slot = ParkingSlot.builder()
                .floor(floor)
                .type(type)
                .slotNumber(slotNumber)
                .status(SlotStatus.FREE)
                .build();
        ParkingSlot savedSlot = slotRepo.save(slot);
        return new SlotResponse(savedSlot.getId(), savedSlot.getType(), savedSlot.getFloor().getLevel(), savedSlot.getSlotNumber());
    }

    @Transactional
    public void removeSlot(Long slotId) {
        ParkingSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new EntityNotFoundException("Slot not found: " + slotId));

        if (slot.getStatus() == SlotStatus.OCCUPIED) {
            throw new IllegalStateException("Cannot remove occupied slot: " + slotId);
        }

        slotRepo.delete(slot);
    }
}
