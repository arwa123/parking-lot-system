package com.example.parkinglot.dto;

import com.example.parkinglot.enums.VehicleType;

public record SlotResponse(
        Long id,
        VehicleType type,
        int floorLevel,
        String slotNumber
) {
}