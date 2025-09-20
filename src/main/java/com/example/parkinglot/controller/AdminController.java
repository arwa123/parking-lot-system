package com.example.parkinglot.controller;
import com.example.parkinglot.entity.PricingRule;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.PricingRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/api/admin") @RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final PricingRuleRepository pricingRepo;

    @PostMapping("/pricing")
    public PricingRule upsert(@RequestParam("type") VehicleType type,
                              @RequestParam("freeMinutes") int freeMinutes,
                              @RequestParam("hourlyRate") BigDecimal hourlyRate) {
        return pricingRepo.findByType(type)
                .map(existing -> {
                    existing.setFreeMinutes(freeMinutes);
                    existing.setHourlyRate(hourlyRate);
                    return pricingRepo.save(existing);
                })
                .orElseGet(() -> pricingRepo.save(PricingRule.builder()
                        .type(type)
                        .freeMinutes(freeMinutes)
                        .hourlyRate(hourlyRate)
                        .build()));
    }

    @GetMapping("/pricing")
    public List<PricingRule> list() { return pricingRepo.findAll(); }
}
