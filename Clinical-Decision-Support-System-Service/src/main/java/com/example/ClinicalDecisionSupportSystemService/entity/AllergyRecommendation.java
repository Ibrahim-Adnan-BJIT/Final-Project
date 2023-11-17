package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergy_recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AllergyRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long allergyId;
    private String peanut;
    private String lactose;
    private String gluten;
    private String shellfish;
    private String tree_nut;
    private String egg;
    private String soy;
}
