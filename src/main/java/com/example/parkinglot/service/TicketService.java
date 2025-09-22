package com.example.parkinglot.service;
import com.example.parkinglot.dto.EntryRequest;
import com.example.parkinglot.dto.EntryResponse;
import com.example.parkinglot.dto.ExitRequest;
import com.example.parkinglot.dto.ExitResponse;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.*;
import com.example.parkinglot.repository.*;
import com.example.parkinglot.service.strategy.SlotAllocationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service @RequiredArgsConstructor
public class TicketService {
    private final VehicleRepository vehicleRepo;
    private final TicketRepository ticketRepo;
    private final ParkingSlotRepository slotRepo;
    private final SlotAllocationStrategy strategy;

    @Transactional
    public EntryResponse enter(EntryRequest req, String ownerId) {
        Vehicle vehicle = vehicleRepo.findByPlateNo(req.plateNo())
                .orElseGet(() -> vehicleRepo.save(Vehicle.builder()
                        .plateNo(req.plateNo())
                        .type(req.type())
                        .ownerId(ownerId).build()));

        // prevent duplicate active ticket
        ticketRepo.findByVehicleAndStatus(vehicle, TicketStatus.ACTIVE).ifPresent(t -> {
            throw new IllegalStateException("Vehicle already has an active ticket: " + t.getId());
        });

        var slotOpt = strategy.allocate(req.type());
        if (slotOpt.isEmpty()) {
            throw new IllegalStateException("Parking lot is full for type " + req.type());
        }
        ParkingSlot slot = slotOpt.get();
        if (slot.getStatus() != SlotStatus.FREE) {
            throw new IllegalStateException("Selected slot not free due to concurrent allocation");
        }
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepo.save(slot);

        Ticket ticket = Ticket.builder()
                .vehicle(vehicle)
                .slot(slot)
                .entryTime(LocalDateTime.now())
                .status(TicketStatus.ACTIVE)
                .build();
        ticketRepo.save(ticket);
        log.debug("Ticket created is : {}", ticket);
        return new EntryResponse(ticket.getId(), slot.getSlotNumber(), "Ticket created");
    }

    @Transactional
    public ExitResponse markExit(ExitRequest req) {
        Ticket ticket = ticketRepo.findById(req.ticketId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket already closed");
        }
        ticket.setExitTime(LocalDateTime.now());
        ticketRepo.save(ticket);
        log.debug("Ticket updated is : {}", ticket);
        return new ExitResponse(
                ticket.getId(),
                ticket.getVehicle().getPlateNo(),
                ticket.getSlot().getSlotNumber(),
                "Floor " + ticket.getSlot().getFloor().getLevel(),
                ticket.getSlot().getFloor().getParkingLot().getLocation()
        );
    }

}
