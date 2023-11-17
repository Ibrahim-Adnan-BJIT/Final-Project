package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diabetes_recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DiabetesRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long diabetesId;
    private String normal;
    private String type1;
    private String type2;
}
