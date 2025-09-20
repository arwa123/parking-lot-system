package com.example.parkinglot.controller;
import com.example.parkinglot.dto.PaymentRequest;
import com.example.parkinglot.dto.ReceiptResponse;
import com.example.parkinglot.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/payments") @RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ReceiptResponse pay(@RequestBody PaymentRequest req) {
        return paymentService.payAndFree(req);
    }
}
