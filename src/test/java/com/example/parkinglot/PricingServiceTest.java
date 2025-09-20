package com.example.parkinglot;
import com.example.parkinglot.entity.*;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.PricingRuleRepository;
import com.example.parkinglot.service.PricingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceTest {
    @Test
    void testPricing() {
        var repo = Mockito.mock(PricingRuleRepository.class);
        var svc = new PricingService(repo);
        Mockito.when(repo.findByType(VehicleType.CAR)).thenReturn(java.util.Optional.of(
                PricingRule.builder().type(VehicleType.CAR).freeMinutes(10).hourlyRate(new BigDecimal("50")).build()
        ));
        Vehicle v = Vehicle.builder().type(VehicleType.CAR).build();
        Ticket t = Ticket.builder().vehicle(v)
                .entryTime(LocalDateTime.now().minusMinutes(140))
                .exitTime(LocalDateTime.now()).build();
        var amt = svc.calculate(t);
        assertEquals(new BigDecimal("150"), amt); // 130 billable -> ceil to 3? actually 130-10=120 -> 2 hours
    }
}
