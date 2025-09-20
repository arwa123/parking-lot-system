package com.example.parkinglot.dto;
import com.example.parkinglot.enums.VehicleType;
public record EntryRequest(String plateNo, VehicleType type, Long entryGateId) {}
