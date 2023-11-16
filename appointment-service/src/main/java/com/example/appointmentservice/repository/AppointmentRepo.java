package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatientId(long id);
    List<Appointment>findByDoctorId(long id);
}
