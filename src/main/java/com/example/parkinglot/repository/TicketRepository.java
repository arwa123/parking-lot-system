package com.example.parkinglot.repository;
import com.example.parkinglot.entity.Ticket;
import com.example.parkinglot.entity.Vehicle;
import com.example.parkinglot.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByVehicleAndStatus(Vehicle vehicle, TicketStatus status);
}
