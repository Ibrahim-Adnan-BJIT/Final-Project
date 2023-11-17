package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bmi_recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BmiRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bmiId;
    private String underweight;
    private String normalWeight;
    private String overweight;
    private String obesityClassI;
    private String obesityClassII;
    private String obesityClassIII;
}
