package com.healthmanagement.doctorservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "appointment")
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
    private String doctorName;
    private long patientId;
    private LocalDate date;
    private String location;
    private long roomNumber;
    private String status;
    private String type;
}
