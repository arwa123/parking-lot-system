package com.example.parkinglot.controller;
import com.example.parkinglot.dto.*;
import com.example.parkinglot.entity.Ticket;
import com.example.parkinglot.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/tickets") @RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/entry")
    public EntryResponse enter(@RequestBody EntryRequest req, @AuthenticationPrincipal OAuth2User user) {
        String ownerId = user != null ? user.getName() : "ANON";
        return ticketService.enter(req, ownerId);
    }

    @PostMapping("/exit")
    public ExitResponse exit(@RequestBody ExitRequest req) {
        return ticketService.markExit(req);
    }
}
