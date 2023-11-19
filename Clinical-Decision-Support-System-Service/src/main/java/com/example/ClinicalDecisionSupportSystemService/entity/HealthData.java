package com.example.ClinicalDecisionSupportSystemService.entity;

import com.sun.jdi.Type;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "health_data")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class HealthData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordId;
    private long patientId;
    private double height;
    private int weight;
    private double bmi;
    @Enumerated(EnumType.STRING)
    private BloodPressure bloodPressure;
    @Enumerated(EnumType.STRING)
    private Allergy allergy;
    @Enumerated(EnumType.STRING)
    private Diabetes diabetes;
}
