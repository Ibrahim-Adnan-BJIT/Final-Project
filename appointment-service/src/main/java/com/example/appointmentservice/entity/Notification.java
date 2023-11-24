package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;
    private long patientId;
    private long doctorId;
    private String message;
    private boolean patientStatus;
    private boolean doctorStatus;
}
