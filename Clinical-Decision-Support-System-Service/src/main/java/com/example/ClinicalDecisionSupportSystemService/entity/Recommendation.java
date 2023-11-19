package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recommendationId;
    private long patientId;
    private String diabetesRecom;
    private String allergyRecom;
    private String bloodPressureRecom;
    private String bmiRecom;
}
