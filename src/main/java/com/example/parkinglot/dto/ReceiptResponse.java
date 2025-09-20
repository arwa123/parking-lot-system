package com.example.parkinglot.dto;
import java.math.BigDecimal;
public record ReceiptResponse(Long ticketId, BigDecimal amount, String status) {}
