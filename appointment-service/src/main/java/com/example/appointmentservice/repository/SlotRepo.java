package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepo extends JpaRepository<Slot,Long> {
}
