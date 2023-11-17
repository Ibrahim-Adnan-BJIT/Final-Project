package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blood_pressure_recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BloodPressureRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bloodPressureRecommendationId;

    private String normal;
    private String elevated;
    private String hypertension_stage_1;
    private String hypertension_stage_2;
    private String hypertensive_crisis;
}
