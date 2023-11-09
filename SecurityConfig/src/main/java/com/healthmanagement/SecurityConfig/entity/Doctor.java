package com.healthmanagement.SecurityConfig.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Setter
@Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorId;
    private long userId;
    private Speciality speciality;
    private String qualification;
}
