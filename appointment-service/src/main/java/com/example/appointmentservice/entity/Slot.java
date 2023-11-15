package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "slots")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long slotId;

    private long doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String type;
}
