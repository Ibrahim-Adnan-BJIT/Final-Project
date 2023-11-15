package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;
    private long doctorId;
    private long patientId;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type;
    private String roomNumber;
    private String status;
    @OneToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
}
