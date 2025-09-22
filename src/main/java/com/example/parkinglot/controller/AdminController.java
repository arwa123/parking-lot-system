package com.example.parkinglot.controller;

import com.example.parkinglot.dto.SlotResponse;
import com.example.parkinglot.entity.PricingRule;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.ParkingSlotRepository;
import com.example.parkinglot.repository.PricingRuleRepository;
import com.example.parkinglot.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final PricingRuleRepository pricingRepo;

    private final ParkingSlotRepository parkingSlotRepository;
    private final SlotService slotService;


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
    public List<PricingRule> list() {
        return pricingRepo.findAll();
    }

    @GetMapping("/slots")
    public List<SlotResponse> slotslist() {
        return parkingSlotRepository.findAll().stream()
                .map(s -> new SlotResponse(s.getId(), s.getType(),
                        s.getFloor().getLevel(),s.getSlotNumber()))
                .toList();
    }

    // Add a new slot
    @PostMapping("/slots")
    public SlotResponse addSlot(@RequestParam("type") VehicleType type,
                               @RequestParam("floorId") Long floorId,
                               @RequestParam("slotNumber") String slotNumber) {
        return slotService.addSlot(floorId, type, slotNumber);
    }

    // Remove a slot
    @DeleteMapping("slots/{slotId}")
    public void removeSlot(@PathVariable("slotId") Long slotId) {
        slotService.removeSlot(slotId);
    }
}
