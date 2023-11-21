package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepo extends JpaRepository<Medicine,Long> {
}
