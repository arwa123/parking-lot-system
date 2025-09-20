package com.example.parkinglot.repository;
import com.example.parkinglot.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Long> {}
