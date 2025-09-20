package com.example.parkinglot.repository;
import com.example.parkinglot.entity.PricingRule;
import com.example.parkinglot.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    Optional<PricingRule> findByType(VehicleType type);
}
