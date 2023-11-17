package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.DiabetesRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiabetesRecommendationRepo extends JpaRepository<DiabetesRecommendation,Long> {
}
