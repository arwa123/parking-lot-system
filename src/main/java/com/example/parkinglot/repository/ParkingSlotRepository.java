package com.example.parkinglot.repository;
import com.example.parkinglot.entity.ParkingSlot;
import com.example.parkinglot.enums.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ParkingSlot s where s.type = :type and s.status = 'FREE' order by s.floor.level asc, s.slotNumber asc")
    List<ParkingSlot> findNearestFreeSlotForUpdate(@Param("type") VehicleType type);
}
