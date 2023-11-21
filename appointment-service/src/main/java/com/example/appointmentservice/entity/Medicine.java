package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "medicine")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long medicineId;
    private String medicineName;
    private String description;
    private LocalDate expire;
}
