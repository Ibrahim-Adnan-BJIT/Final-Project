package com.healthmanagement.doctorservice.repository;

import com.healthmanagement.doctorservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
}
