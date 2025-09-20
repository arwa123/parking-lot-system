package com.example.parkinglot.service;
import com.example.parkinglot.entity.PricingRule;
import com.example.parkinglot.entity.Ticket;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.PricingRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;

@Service @RequiredArgsConstructor
public class PricingService {
    private final PricingRuleRepository pricingRepo;

    public BigDecimal calculate(Ticket ticket) {
        VehicleType type = ticket.getVehicle().getType();
        PricingRule rule = pricingRepo.findByType(type)
                .orElseThrow(() -> new IllegalStateException("No pricing rule for " + type));
        var end = ticket.getExitTime() != null ? ticket.getExitTime() : ticket.getEntryTime();
        long minutes = Duration.between(ticket.getEntryTime(), end).toMinutes();
        long billable = Math.max(0, minutes - rule.getFreeMinutes());
        long hours = (billable + 59) / 60; // ceil
        return rule.getHourlyRate().multiply(BigDecimal.valueOf(hours));
    }
}
