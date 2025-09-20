package com.example.parkinglot.dto;

public record ExitResponse(Long ticketId, String plateNo, String slotNumber, String floor, String lot) {}
