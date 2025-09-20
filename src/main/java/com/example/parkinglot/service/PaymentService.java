package com.example.parkinglot.service;
import com.example.parkinglot.dto.PaymentRequest;
import com.example.parkinglot.dto.ReceiptResponse;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.*;
import com.example.parkinglot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class PaymentService {
    private final TicketRepository ticketRepo;
    private final PaymentRepository paymentRepo;
    private final PricingService pricingService;
    private final ParkingSlotRepository slotRepo;

    @Transactional
    public ReceiptResponse payAndFree(PaymentRequest req) {
        Ticket ticket = ticketRepo.findById(req.ticketId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new IllegalStateException("Ticket not active");
        }
        if (ticket.getExitTime() == null) {
            throw new IllegalStateException("Please mark exit before payment");
        }
        BigDecimal amount = pricingService.calculate(ticket);

        Payment payment = Payment.builder()
                .ticket(ticket)
                .amount(amount)
                .status(PaymentStatus.INITIATED)
                .timestamp(LocalDateTime.now())
                .txnRef(UUID.randomUUID().toString())
                .build();
        paymentRepo.save(payment);

        // Simulate payment processor: succeed unless method is "FAIL"
        if ("FAIL".equalsIgnoreCase(req.paymentMethod())) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepo.save(payment);
            return new ReceiptResponse(ticket.getId(), amount, "FAILED");
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepo.save(payment);

        // free slot atomically
        ParkingSlot slot = ticket.getSlot();
        slot.setStatus(SlotStatus.FREE);
        slotRepo.save(slot);
        ticket.setStatus(TicketStatus.CLOSED);
        ticketRepo.save(ticket);

        return new ReceiptResponse(ticket.getId(), amount, "SUCCESS");
    }
}
